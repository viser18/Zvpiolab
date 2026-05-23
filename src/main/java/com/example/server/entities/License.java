package com.example.server.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "licenses")
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "type_id", nullable = false)
    private Long typeId;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_activation_date")
    private LocalDate firstActivationDate;

    @Column(name = "ending_date")
    private LocalDate endingDate;

    @Column(nullable = false)
    private Boolean blocked = false;

    @Column(name = "device_count", nullable = false)
    private Integer deviceCount = 1;

    @Column(length = 500)
    private String description;

    public License() {}

    // Getters
    public Long getId() { return id; }
    public String getCode() { return code; }
    public Long getProductId() { return productId; }
    public Long getTypeId() { return typeId; }
    public Long getOwnerId() { return ownerId; }
    public Long getUserId() { return userId; }
    public LocalDate getFirstActivationDate() { return firstActivationDate; }
    public LocalDate getEndingDate() { return endingDate; }
    public Boolean getBlocked() { return blocked; }
    public Integer getDeviceCount() { return deviceCount; }
    public String getDescription() { return description; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setProductId(Long productId) { this.productId = productId; }
    public void setTypeId(Long typeId) { this.typeId = typeId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setFirstActivationDate(LocalDate firstActivationDate) { this.firstActivationDate = firstActivationDate; }
    public void setEndingDate(LocalDate endingDate) { this.endingDate = endingDate; }
    public void setBlocked(Boolean blocked) { this.blocked = blocked; }
    public void setDeviceCount(Integer deviceCount) { this.deviceCount = deviceCount; }
    public void setDescription(String description) { this.description = description; }

    public static LicenseBuilder builder() {
        return new LicenseBuilder();
    }

    public static class LicenseBuilder {
        private Long id;
        private String code;
        private Long productId;
        private Long typeId;
        private Long ownerId;
        private Long userId;
        private LocalDate firstActivationDate;
        private LocalDate endingDate;
        private Boolean blocked = false;
        private Integer deviceCount = 1;
        private String description;

        public LicenseBuilder id(Long id) { this.id = id; return this; }
        public LicenseBuilder code(String code) { this.code = code; return this; }
        public LicenseBuilder productId(Long productId) { this.productId = productId; return this; }
        public LicenseBuilder typeId(Long typeId) { this.typeId = typeId; return this; }
        public LicenseBuilder ownerId(Long ownerId) { this.ownerId = ownerId; return this; }
        public LicenseBuilder userId(Long userId) { this.userId = userId; return this; }
        public LicenseBuilder firstActivationDate(LocalDate firstActivationDate) { this.firstActivationDate = firstActivationDate; return this; }
        public LicenseBuilder endingDate(LocalDate endingDate) { this.endingDate = endingDate; return this; }
        public LicenseBuilder blocked(Boolean blocked) { this.blocked = blocked; return this; }
        public LicenseBuilder deviceCount(Integer deviceCount) { this.deviceCount = deviceCount; return this; }
        public LicenseBuilder description(String description) { this.description = description; return this; }

        public License build() {
            License license = new License();
            license.id = this.id;
            license.code = this.code;
            license.productId = this.productId;
            license.typeId = this.typeId;
            license.ownerId = this.ownerId;
            license.userId = this.userId;
            license.firstActivationDate = this.firstActivationDate;
            license.endingDate = this.endingDate;
            license.blocked = this.blocked;
            license.deviceCount = this.deviceCount;
            license.description = this.description;
            return license;
        }
    }
}