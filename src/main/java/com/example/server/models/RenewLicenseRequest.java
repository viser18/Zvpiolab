package com.example.server.models;

public class RenewLicenseRequest {
    private String activationKey;

    public RenewLicenseRequest() {}

    public String getActivationKey() { return activationKey; }
    public void setActivationKey(String activationKey) { this.activationKey = activationKey; }
}