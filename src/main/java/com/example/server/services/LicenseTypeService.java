package com.example.server.services;

import com.example.server.entities.LicenseType;
import com.example.server.repositories.LicenseTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LicenseTypeService {

    private final LicenseTypeRepository licenseTypeRepository;

    public LicenseTypeService(LicenseTypeRepository licenseTypeRepository) {
        this.licenseTypeRepository = licenseTypeRepository;
    }

    @Transactional
    public LicenseType createLicenseType(String name, Integer defaultDurationInDays, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("License type name is required");
        }
        if (defaultDurationInDays == null || defaultDurationInDays <= 0) {
            throw new RuntimeException("Default duration must be positive");
        }
        if (licenseTypeRepository.existsByName(name)) {
            throw new RuntimeException("License type with name '" + name + "' already exists");
        }

        // Создаём без builder
        LicenseType licenseType = new LicenseType();
        licenseType.setName(name);
        licenseType.setDefaultDurationInDays(defaultDurationInDays);
        licenseType.setDescription(description);

        return licenseTypeRepository.save(licenseType);
    }

    public LicenseType getTypeOrFail(Long id) {
        return licenseTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("License type not found with id: " + id));
    }
}