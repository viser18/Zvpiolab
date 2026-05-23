package com.example.server.entities;

import com.example.server.models.LicenseEventStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "license_history")
public class LicenseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_id", nullable = false)
    private Long licenseId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LicenseEventStatus status;

    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate = LocalDateTime.now();

    @Column(length = 1000)
    private String description;

    public LicenseHistory() {}

    public LicenseHistory(Long id, Long licenseId, Long userId, LicenseEventStatus status,
                          LocalDateTime changeDate, String description) {
        this.id = id;
        this.licenseId = licenseId;
        this.userId = userId;
        this.status = status;
        this.changeDate = changeDate;
        this.description = description;
    }

    // Getters
    public Long getId() { return id; }
    public Long getLicenseId() { return licenseId; }
    public Long getUserId() { return userId; }
    public LicenseEventStatus getStatus() { return status; }
    public LocalDateTime getChangeDate() { return changeDate; }
    public String getDescription() { return description; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setLicenseId(Long licenseId) { this.licenseId = licenseId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setStatus(LicenseEventStatus status) { this.status = status; }
    public void setChangeDate(LocalDateTime changeDate) { this.changeDate = changeDate; }
    public void setDescription(String description) { this.description = description; }

    public static LicenseHistoryBuilder builder() {
        return new LicenseHistoryBuilder();
    }

    public static class LicenseHistoryBuilder {
        private Long id;
        private Long licenseId;
        private Long userId;
        private LicenseEventStatus status;
        private LocalDateTime changeDate = LocalDateTime.now();
        private String description;

        public LicenseHistoryBuilder id(Long id) { this.id = id; return this; }
        public LicenseHistoryBuilder licenseId(Long licenseId) { this.licenseId = licenseId; return this; }
        public LicenseHistoryBuilder userId(Long userId) { this.userId = userId; return this; }
        public LicenseHistoryBuilder status(LicenseEventStatus status) { this.status = status; return this; }
        public LicenseHistoryBuilder changeDate(LocalDateTime changeDate) { this.changeDate = changeDate; return this; }
        public LicenseHistoryBuilder description(String description) { this.description = description; return this; }

        public LicenseHistory build() {
            return new LicenseHistory(id, licenseId, userId, status, changeDate, description);
        }
    }
}