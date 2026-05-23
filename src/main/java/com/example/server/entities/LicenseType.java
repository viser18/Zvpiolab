package com.example.server.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "license_types")
public class LicenseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "default_duration_in_days", nullable = false)
    private Integer defaultDurationInDays;

    @Column(length = 500)
    private String description;

    public LicenseType() {}

    public LicenseType(Long id, String name, Integer defaultDurationInDays, String description) {
        this.id = id;
        this.name = name;
        this.defaultDurationInDays = defaultDurationInDays;
        this.description = description;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getDefaultDurationInDays() { return defaultDurationInDays; }
    public String getDescription() { return description; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDefaultDurationInDays(Integer defaultDurationInDays) { this.defaultDurationInDays = defaultDurationInDays; }
    public void setDescription(String description) { this.description = description; }

    public static LicenseTypeBuilder builder() {
        return new LicenseTypeBuilder();
    }

    public static class LicenseTypeBuilder {
        private Long id;
        private String name;
        private Integer defaultDurationInDays;
        private String description;

        public LicenseTypeBuilder id(Long id) { this.id = id; return this; }
        public LicenseTypeBuilder name(String name) { this.name = name; return this; }
        public LicenseTypeBuilder defaultDurationInDays(Integer defaultDurationInDays) { this.defaultDurationInDays = defaultDurationInDays; return this; }
        public LicenseTypeBuilder description(String description) { this.description = description; return this; }

        public LicenseType build() {
            return new LicenseType(id, name, defaultDurationInDays, description);
        }
    }
}