package com.example.server.services;

import com.example.server.entities.Device;
import com.example.server.repositories.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public Device registerDevice(String macAddress, String name, Long userId) {
        if (deviceRepository.existsByMacAddress(macAddress)) {
            Device existingDevice = deviceRepository.findByMacAddress(macAddress).get();
            if (!existingDevice.getUserId().equals(userId)) {
                throw new RuntimeException("Device with MAC address " + macAddress + " is already registered to another user");
            }
            return existingDevice;
        }

        Device device = Device.builder()
                .macAddress(macAddress)
                .name(name != null ? name : "Device " + macAddress)
                .userId(userId)
                .build();

        return deviceRepository.save(device);
    }

    public List<Device> getUserDevices(Long userId) {
        return deviceRepository.findByUserId(userId);
    }

    @Transactional
    public Device updateDeviceName(Long deviceId, String name, Long userId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        if (!device.getUserId().equals(userId)) {
            throw new RuntimeException("Device does not belong to user");
        }

        device.setName(name);
        return deviceRepository.save(device);
    }

    @Transactional
    public void deleteDevice(Long deviceId, Long userId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        if (!device.getUserId().equals(userId)) {
            throw new RuntimeException("Device does not belong to user");
        }

        deviceRepository.delete(device);
    }

    public boolean deviceExistsAndBelongsToUser(String macAddress, Long userId) {
        return deviceRepository.findByUserIdAndMacAddress(userId, macAddress).isPresent();
    }
}