package com.example.server.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "mac_address", nullable = false, unique = true)
    private String macAddress;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public Device() {}

    public Device(Long id, String name, String macAddress, Long userId) {
        this.id = id;
        this.name = name;
        this.macAddress = macAddress;
        this.userId = userId;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getMacAddress() { return macAddress; }
    public Long getUserId() { return userId; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
    public void setUserId(Long userId) { this.userId = userId; }

    public static DeviceBuilder builder() {
        return new DeviceBuilder();
    }

    public static class DeviceBuilder {
        private Long id;
        private String name;
        private String macAddress;
        private Long userId;

        public DeviceBuilder id(Long id) { this.id = id; return this; }
        public DeviceBuilder name(String name) { this.name = name; return this; }
        public DeviceBuilder macAddress(String macAddress) { this.macAddress = macAddress; return this; }
        public DeviceBuilder userId(Long userId) { this.userId = userId; return this; }

        public Device build() {
            return new Device(id, name, macAddress, userId);
        }
    }
}