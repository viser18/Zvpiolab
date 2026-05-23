package com.example.server.models;

public class DeviceRegisterRequest {
    private String macAddress;
    private String name;

    public DeviceRegisterRequest() {}

    public DeviceRegisterRequest(String macAddress, String name) {
        this.macAddress = macAddress;
        this.name = name;
    }

    public String getMacAddress() { return macAddress; }
    public String getName() { return name; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
    public void setName(String name) { this.name = name; }
}