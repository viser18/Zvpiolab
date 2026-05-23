package com.example.server.models;

public class CheckLicenseRequest {
    private String deviceMac;
    private Long productId;

    public CheckLicenseRequest() {}

    public String getDeviceMac() { return deviceMac; }
    public Long getProductId() { return productId; }

    public void setDeviceMac(String deviceMac) { this.deviceMac = deviceMac; }
    public void setProductId(Long productId) { this.productId = productId; }
}