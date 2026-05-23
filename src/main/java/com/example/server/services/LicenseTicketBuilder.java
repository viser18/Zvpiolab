package com.example.server.services;

import com.example.server.entities.*;
import com.example.server.models.LicenseTicket;
import com.example.server.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LicenseTicketBuilder {

    private final ProductRepository productRepository;
    private final LicenseTypeRepository licenseTypeRepository;
    private final UserRepository userRepository;
    private final DeviceLicenseRepository deviceLicenseRepository;
    private final DeviceRepository deviceRepository;
    private final LicenseRepository licenseRepository;

    public LicenseTicketBuilder(ProductRepository productRepository,
                                LicenseTypeRepository licenseTypeRepository,
                                UserRepository userRepository,
                                DeviceLicenseRepository deviceLicenseRepository,
                                DeviceRepository deviceRepository,
                                LicenseRepository licenseRepository) {
        this.productRepository = productRepository;
        this.licenseTypeRepository = licenseTypeRepository;
        this.userRepository = userRepository;
        this.deviceLicenseRepository = deviceLicenseRepository;
        this.deviceRepository = deviceRepository;
        this.licenseRepository = licenseRepository;
    }

    public LicenseTicket buildTicket(License license) {
        Product product = productRepository.findById(license.getProductId()).orElse(null);
        LicenseType licenseType = licenseTypeRepository.findById(license.getTypeId()).orElse(null);
        User owner = userRepository.findById(license.getOwnerId()).orElse(null);
        User user = license.getUserId() != null ? userRepository.findById(license.getUserId()).orElse(null) : null;

        Integer activatedDevicesCount = licenseRepository.countActivatedDevices(license.getId());

        List<LicenseTicket.DeviceInfo> devices = List.of();
        if (license.getUserId() != null) {
            List<DeviceLicense> deviceLicenses = deviceLicenseRepository.findByLicenseId(license.getId());
            devices = deviceLicenses.stream()
                    .map(dl -> {
                        Device d = deviceRepository.findById(dl.getDeviceId()).orElse(null);
                        if (d != null) {
                            return new LicenseTicket.DeviceInfo(
                                    d.getId(),
                                    d.getName(),
                                    d.getMacAddress(),
                                    dl.getActivationDate().toLocalDate()
                            );
                        }
                        return null;
                    })
                    .filter(d -> d != null)
                    .collect(Collectors.toList());
        }

        return new LicenseTicket(
                license.getId(),
                license.getCode(),
                product != null ? product.getName() : "Unknown",
                licenseType != null ? licenseType.getName() : "Unknown",
                owner != null ? owner.getEmail() : "Unknown",
                user != null ? user.getEmail() : null,
                license.getFirstActivationDate(),
                license.getEndingDate(),
                license.getBlocked(),
                license.getDeviceCount(),
                activatedDevicesCount,
                devices
        );
    }
}