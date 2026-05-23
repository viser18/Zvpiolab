package com.example.server.models;

public class DeviceUpdateRequest {
    private String name;

    public DeviceUpdateRequest() {}

    public DeviceUpdateRequest(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}