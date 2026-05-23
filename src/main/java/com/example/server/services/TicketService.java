package com.example.server.services;

import com.example.server.entities.*;
import com.example.server.models.Ticket;
import com.example.server.models.TicketResponse;
import com.example.server.repositories.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TicketService {

    private final LicenseRepository licenseRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceLicenseRepository deviceLicenseRepository;

    @Value("${jwt.access.secret}")
    private String ticketSecret;

    public TicketService(LicenseRepository licenseRepository,
                         DeviceRepository deviceRepository,
                         DeviceLicenseRepository deviceLicenseRepository) {
        this.licenseRepository = licenseRepository;
        this.deviceRepository = deviceRepository;
        this.deviceLicenseRepository = deviceLicenseRepository;
    }

    private Key getTicketSigningKey() {
        return Keys.hmacShaKeyFor(ticketSecret.getBytes());
    }

    public TicketResponse generateTicket(String licenseCode, String macAddress, Long userId) {
        License license = licenseRepository.findByCode(licenseCode)
                .orElseThrow(() -> new RuntimeException("License not found"));

        if (!license.getUserId().equals(userId)) {
            throw new RuntimeException("License does not belong to user");
        }

        Device device = deviceRepository.findByMacAddress(macAddress)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        if (!device.getUserId().equals(userId)) {
            throw new RuntimeException("Device does not belong to user");
        }

        DeviceLicense deviceLicense = deviceLicenseRepository.findByLicenseIdAndDeviceId(license.getId(), device.getId())
                .orElseThrow(() -> new RuntimeException("License not activated on this device"));

        Ticket ticket = new Ticket();
        ticket.setServerTime(LocalDateTime.now());
        ticket.setTimeToLive(3600L);
        ticket.setActivationDate(deviceLicense.getActivationDate());
        ticket.setExpirationDate(license.getEndingDate().atStartOfDay());
        ticket.setUserId(userId);
        ticket.setDeviceId(device.getId());
        ticket.setBlocked(license.getBlocked());

        String signature = generateTicketSignature(ticket);

        TicketResponse response = new TicketResponse();
        response.setTicket(ticket);
        response.setSignature(signature);

        return response;
    }

    public TicketResponse generateTicketByLicenseId(Long licenseId, Long deviceId, Long userId) {
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new RuntimeException("License not found"));

        if (!license.getUserId().equals(userId)) {
            throw new RuntimeException("License does not belong to user");
        }

        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        if (!device.getUserId().equals(userId)) {
            throw new RuntimeException("Device does not belong to user");
        }

        DeviceLicense deviceLicense = deviceLicenseRepository.findByLicenseIdAndDeviceId(license.getId(), device.getId())
                .orElseThrow(() -> new RuntimeException("License not activated on this device"));

        Ticket ticket = new Ticket();
        ticket.setServerTime(LocalDateTime.now());
        ticket.setTimeToLive(3600L);
        ticket.setActivationDate(deviceLicense.getActivationDate());
        ticket.setExpirationDate(license.getEndingDate().atStartOfDay());
        ticket.setUserId(userId);
        ticket.setDeviceId(device.getId());
        ticket.setBlocked(license.getBlocked());

        String signature = generateTicketSignature(ticket);

        TicketResponse response = new TicketResponse();
        response.setTicket(ticket);
        response.setSignature(signature);

        return response;
    }

    private String generateTicketSignature(Ticket ticket) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600000); // 1 hour

        return Jwts.builder()
                .claim("serverTime", ticket.getServerTime().toString())
                .claim("timeToLive", ticket.getTimeToLive())
                .claim("activationDate", ticket.getActivationDate().toString())
                .claim("expirationDate", ticket.getExpirationDate().toString())
                .claim("userId", ticket.getUserId())
                .claim("deviceId", ticket.getDeviceId())
                .claim("blocked", ticket.getBlocked())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getTicketSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}