package com.example.server.services;

import com.example.server.entities.*;
import com.example.server.models.*;
import com.example.server.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final LicenseTypeRepository licenseTypeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceLicenseRepository deviceLicenseRepository;
    private final LicenseHistoryRepository licenseHistoryRepository;
    private final LicenseTicketBuilder ticketBuilder;

    public LicenseService(LicenseRepository licenseRepository,
                          LicenseTypeRepository licenseTypeRepository,
                          ProductRepository productRepository,
                          UserRepository userRepository,
                          DeviceRepository deviceRepository,
                          DeviceLicenseRepository deviceLicenseRepository,
                          LicenseHistoryRepository licenseHistoryRepository,
                          LicenseTicketBuilder ticketBuilder) {
        this.licenseRepository = licenseRepository;
        this.licenseTypeRepository = licenseTypeRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.deviceLicenseRepository = deviceLicenseRepository;
        this.licenseHistoryRepository = licenseHistoryRepository;
        this.ticketBuilder = ticketBuilder;
    }

    @Transactional
    public License createLicense(CreateLicenseRequest request, Long adminId) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getIsBlocked()) {
            throw new RuntimeException("Cannot create license for blocked product");
        }

        LicenseType licenseType = licenseTypeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new RuntimeException("License type not found"));

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        String code = generateLicenseCode();

        License license = License.builder()
                .code(code)
                .productId(request.getProductId())
                .typeId(request.getTypeId())
                .ownerId(request.getOwnerId())
                .deviceCount(request.getDeviceCount() != null ? request.getDeviceCount() : 1)
                .description(request.getDescription())
                .blocked(false)
                .build();

        License savedLicense = licenseRepository.save(license);

        LicenseHistory history = LicenseHistory.builder()
                .licenseId(savedLicense.getId())
                .userId(adminId)
                .status(LicenseEventStatus.CREATED)
                .description("License created for user: " + owner.getEmail())
                .build();
        licenseHistoryRepository.save(history);

        return savedLicense;
    }

    @Transactional
    public LicenseTicket activateLicense(ActivateLicenseRequest request, Long userId) {
        License license = licenseRepository.findByCode(request.getActivationKey())
                .orElseThrow(() -> new RuntimeException("License not found"));

        if (license.getBlocked()) {
            throw new RuntimeException("License is blocked");
        }

        if (license.getUserId() != null && !license.getUserId().equals(userId)) {
            throw new RuntimeException("License already activated by another user");
        }

        Device device = deviceRepository.findByMacAddress(request.getDeviceMac())
                .orElseThrow(() -> new RuntimeException("Device not found. Please register device first."));

        if (!device.getUserId().equals(userId)) {
            throw new RuntimeException("Device does not belong to user");
        }

        if (deviceLicenseRepository.findByLicenseIdAndDeviceId(license.getId(), device.getId()).isPresent()) {
            throw new RuntimeException("License already activated on this device");
        }

        Integer activatedDevices = licenseRepository.countActivatedDevices(license.getId());
        if (activatedDevices >= license.getDeviceCount()) {
            throw new RuntimeException("Device limit reached for this license");
        }

        if (license.getUserId() == null) {
            license.setUserId(userId);
            license.setFirstActivationDate(LocalDate.now());

            LicenseType licenseType = licenseTypeRepository.findById(license.getTypeId())
                    .orElseThrow(() -> new RuntimeException("License type not found"));
            license.setEndingDate(LocalDate.now().plusDays(licenseType.getDefaultDurationInDays()));
        }

        licenseRepository.save(license);

        DeviceLicense deviceLicense = DeviceLicense.builder()
                .licenseId(license.getId())
                .deviceId(device.getId())
                .build();
        deviceLicenseRepository.save(deviceLicense);

        LicenseHistory history = LicenseHistory.builder()
                .licenseId(license.getId())
                .userId(userId)
                .status(LicenseEventStatus.ACTIVATED)
                .description("License activated on device: " + device.getMacAddress())
                .build();
        licenseHistoryRepository.save(history);

        return ticketBuilder.buildTicket(license);
    }

    @Transactional
    public LicenseTicket renewLicense(RenewLicenseRequest request, Long userId) {
        License license = licenseRepository.findByCode(request.getActivationKey())
                .orElseThrow(() -> new RuntimeException("License not found"));

        if (!license.getUserId().equals(userId)) {
            throw new RuntimeException("License does not belong to user");
        }

        if (license.getBlocked()) {
            throw new RuntimeException("License is blocked");
        }

        LicenseType licenseType = licenseTypeRepository.findById(license.getTypeId())
                .orElseThrow(() -> new RuntimeException("License type not found"));

        LocalDate newEndingDate = license.getEndingDate() != null && license.getEndingDate().isAfter(LocalDate.now())
                ? license.getEndingDate().plusDays(licenseType.getDefaultDurationInDays())
                : LocalDate.now().plusDays(licenseType.getDefaultDurationInDays());

        license.setEndingDate(newEndingDate);
        licenseRepository.save(license);

        LicenseHistory history = LicenseHistory.builder()
                .licenseId(license.getId())
                .userId(userId)
                .status(LicenseEventStatus.RENEWED)
                .description("License renewed until: " + newEndingDate)
                .build();
        licenseHistoryRepository.save(history);

        return ticketBuilder.buildTicket(license);
    }

    @Transactional
    public LicenseTicket checkLicense(CheckLicenseRequest request, Long userId) {
        Device device = deviceRepository.findByMacAddress(request.getDeviceMac())
                .orElseThrow(() -> new RuntimeException("Device not found"));

        if (!device.getUserId().equals(userId)) {
            throw new RuntimeException("Device does not belong to user");
        }

        License license = licenseRepository.findActiveByDeviceUserAndProduct(
                        request.getDeviceMac(), userId, request.getProductId())
                .orElseThrow(() -> new RuntimeException("No active license found for this device and product"));

        return ticketBuilder.buildTicket(license);
    }

    @Transactional
    public void blockLicense(Long licenseId, Long adminId, boolean blocked) {
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new RuntimeException("License not found"));

        license.setBlocked(blocked);
        licenseRepository.save(license);

        LicenseHistory history = LicenseHistory.builder()
                .licenseId(licenseId)
                .userId(adminId)
                .status(blocked ? LicenseEventStatus.BLOCKED : LicenseEventStatus.UNBLOCKED)
                .description("License " + (blocked ? "blocked" : "unblocked") + " by admin")
                .build();
        licenseHistoryRepository.save(history);
    }

    private String generateLicenseCode() {
        return "LIC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}