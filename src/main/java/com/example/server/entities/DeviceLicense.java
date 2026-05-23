package com.example.server.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "device_licenses")
public class DeviceLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_id", nullable = false)
    private Long licenseId;

    @Column(name = "device_id", nullable = false)
    private Long deviceId;

    @Column(name = "activation_date", nullable = false)
    private LocalDateTime activationDate = LocalDateTime.now();

    public DeviceLicense() {}

    public DeviceLicense(Long id, Long licenseId, Long deviceId, LocalDateTime activationDate) {
        this.id = id;
        this.licenseId = licenseId;
        this.deviceId = deviceId;
        this.activationDate = activationDate;
    }

    // Getters
    public Long getId() { return id; }
    public Long getLicenseId() { return licenseId; }
    public Long getDeviceId() { return deviceId; }
    public LocalDateTime getActivationDate() { return activationDate; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setLicenseId(Long licenseId) { this.licenseId = licenseId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public void setActivationDate(LocalDateTime activationDate) { this.activationDate = activationDate; }

    public static DeviceLicenseBuilder builder() {
        return new DeviceLicenseBuilder();
    }

    public static class DeviceLicenseBuilder {
        private Long id;
        private Long licenseId;
        private Long deviceId;
        private LocalDateTime activationDate = LocalDateTime.now();

        public DeviceLicenseBuilder id(Long id) { this.id = id; return this; }
        public DeviceLicenseBuilder licenseId(Long licenseId) { this.licenseId = licenseId; return this; }
        public DeviceLicenseBuilder deviceId(Long deviceId) { this.deviceId = deviceId; return this; }
        public DeviceLicenseBuilder activationDate(LocalDateTime activationDate) { this.activationDate = activationDate; return this; }

        public DeviceLicense build() {
            return new DeviceLicense(id, licenseId, deviceId, activationDate);
        }
    }
}