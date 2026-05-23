package com.example.server.entities;

import com.example.server.models.SessionStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "refresh_token", nullable = true, unique = true, length = 512)
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status = SessionStatus.ACTIVE;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;

    public UserSession() {}

    public UserSession(Long id, Long userId, String refreshToken, SessionStatus status,
                       LocalDateTime createdAt, LocalDateTime expiresAt, LocalDateTime revokedAt) {
        this.id = id;
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.status = status;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.revokedAt = revokedAt;
    }

    // Getters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getRefreshToken() { return refreshToken; }
    public SessionStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public LocalDateTime getRevokedAt() { return revokedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public void setStatus(SessionStatus status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public void setRevokedAt(LocalDateTime revokedAt) { this.revokedAt = revokedAt; }

    public static UserSessionBuilder builder() {
        return new UserSessionBuilder();
    }

    public static class UserSessionBuilder {
        private Long id;
        private Long userId;
        private String refreshToken;
        private SessionStatus status = SessionStatus.ACTIVE;
        private LocalDateTime createdAt = LocalDateTime.now();
        private LocalDateTime expiresAt;
        private LocalDateTime revokedAt;

        public UserSessionBuilder id(Long id) { this.id = id; return this; }
        public UserSessionBuilder userId(Long userId) { this.userId = userId; return this; }
        public UserSessionBuilder refreshToken(String refreshToken) { this.refreshToken = refreshToken; return this; }
        public UserSessionBuilder status(SessionStatus status) { this.status = status; return this; }
        public UserSessionBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public UserSessionBuilder expiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; return this; }
        public UserSessionBuilder revokedAt(LocalDateTime revokedAt) { this.revokedAt = revokedAt; return this; }

        public UserSession build() {
            return new UserSession(id, userId, refreshToken, status, createdAt, expiresAt, revokedAt);
        }
    }
}