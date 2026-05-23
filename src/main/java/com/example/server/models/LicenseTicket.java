package com.example.server.models;

import java.time.LocalDate;
import java.util.List;

public class LicenseTicket {
    private Long licenseId;
    private String code;
    private String productName;
    private String licenseType;
    private String ownerEmail;
    private String userEmail;
    private LocalDate firstActivationDate;
    private LocalDate endingDate;
    private Boolean blocked;
    private Integer deviceCount;
    private Integer activatedDevicesCount;
    private List<DeviceInfo> devices;

    public LicenseTicket() {}

    public LicenseTicket(Long licenseId, String code, String productName, String licenseType,
                         String ownerEmail, String userEmail, LocalDate firstActivationDate,
                         LocalDate endingDate, Boolean blocked, Integer deviceCount,
                         Integer activatedDevicesCount, List<DeviceInfo> devices) {
        this.licenseId = licenseId;
        this.code = code;
        this.productName = productName;
        this.licenseType = licenseType;
        this.ownerEmail = ownerEmail;
        this.userEmail = userEmail;
        this.firstActivationDate = firstActivationDate;
        this.endingDate = endingDate;
        this.blocked = blocked;
        this.deviceCount = deviceCount;
        this.activatedDevicesCount = activatedDevicesCount;
        this.devices = devices;
    }

    // Getters and Setters
    public Long getLicenseId() { return licenseId; }
    public String getCode() { return code; }
    public String getProductName() { return productName; }
    public String getLicenseType() { return licenseType; }
    public String getOwnerEmail() { return ownerEmail; }
    public String getUserEmail() { return userEmail; }
    public LocalDate getFirstActivationDate() { return firstActivationDate; }
    public LocalDate getEndingDate() { return endingDate; }
    public Boolean getBlocked() { return blocked; }
    public Integer getDeviceCount() { return deviceCount; }
    public Integer getActivatedDevicesCount() { return activatedDevicesCount; }
    public List<DeviceInfo> getDevices() { return devices; }

    public void setLicenseId(Long licenseId) { this.licenseId = licenseId; }
    public void setCode(String code) { this.code = code; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setLicenseType(String licenseType) { this.licenseType = licenseType; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public void setFirstActivationDate(LocalDate firstActivationDate) { this.firstActivationDate = firstActivationDate; }
    public void setEndingDate(LocalDate endingDate) { this.endingDate = endingDate; }
    public void setBlocked(Boolean blocked) { this.blocked = blocked; }
    public void setDeviceCount(Integer deviceCount) { this.deviceCount = deviceCount; }
    public void setActivatedDevicesCount(Integer activatedDevicesCount) { this.activatedDevicesCount = activatedDevicesCount; }
    public void setDevices(List<DeviceInfo> devices) { this.devices = devices; }

    public static class DeviceInfo {
        private Long deviceId;
        private String deviceName;
        private String macAddress;
        private LocalDate activationDate;

        public DeviceInfo() {}

        public DeviceInfo(Long deviceId, String deviceName, String macAddress, LocalDate activationDate) {
            this.deviceId = deviceId;
            this.deviceName = deviceName;
            this.macAddress = macAddress;
            this.activationDate = activationDate;
        }

        public Long getDeviceId() { return deviceId; }
        public String getDeviceName() { return deviceName; }
        public String getMacAddress() { return macAddress; }
        public LocalDate getActivationDate() { return activationDate; }

        public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
        public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
        public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
        public void setActivationDate(LocalDate activationDate) { this.activationDate = activationDate; }
    }
}