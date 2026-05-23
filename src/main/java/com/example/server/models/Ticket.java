package com.example.server.models;

import java.time.LocalDateTime;

public class Ticket {
    private LocalDateTime serverTime;
    private Long timeToLive;
    private LocalDateTime activationDate;
    private LocalDateTime expirationDate;
    private Long userId;
    private Long deviceId;
    private Boolean blocked;

    public Ticket() {}

    public Ticket(LocalDateTime serverTime, Long timeToLive, LocalDateTime activationDate,
                  LocalDateTime expirationDate, Long userId, Long deviceId, Boolean blocked) {
        this.serverTime = serverTime;
        this.timeToLive = timeToLive;
        this.activationDate = activationDate;
        this.expirationDate = expirationDate;
        this.userId = userId;
        this.deviceId = deviceId;
        this.blocked = blocked;
    }

    // Getters and Setters
    public LocalDateTime getServerTime() { return serverTime; }
    public Long getTimeToLive() { return timeToLive; }
    public LocalDateTime getActivationDate() { return activationDate; }
    public LocalDateTime getExpirationDate() { return expirationDate; }
    public Long getUserId() { return userId; }
    public Long getDeviceId() { return deviceId; }
    public Boolean getBlocked() { return blocked; }

    public void setServerTime(LocalDateTime serverTime) { this.serverTime = serverTime; }
    public void setTimeToLive(Long timeToLive) { this.timeToLive = timeToLive; }
    public void setActivationDate(LocalDateTime activationDate) { this.activationDate = activationDate; }
    public void setExpirationDate(LocalDateTime expirationDate) { this.expirationDate = expirationDate; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public void setBlocked(Boolean blocked) { this.blocked = blocked; }

    public static TicketBuilder builder() {
        return new TicketBuilder();
    }

    public static class TicketBuilder {
        private LocalDateTime serverTime;
        private Long timeToLive;
        private LocalDateTime activationDate;
        private LocalDateTime expirationDate;
        private Long userId;
        private Long deviceId;
        private Boolean blocked;

        public TicketBuilder serverTime(LocalDateTime serverTime) { this.serverTime = serverTime; return this; }
        public TicketBuilder timeToLive(Long timeToLive) { this.timeToLive = timeToLive; return this; }
        public TicketBuilder activationDate(LocalDateTime activationDate) { this.activationDate = activationDate; return this; }
        public TicketBuilder expirationDate(LocalDateTime expirationDate) { this.expirationDate = expirationDate; return this; }
        public TicketBuilder userId(Long userId) { this.userId = userId; return this; }
        public TicketBuilder deviceId(Long deviceId) { this.deviceId = deviceId; return this; }
        public TicketBuilder blocked(Boolean blocked) { this.blocked = blocked; return this; }

        public Ticket build() {
            return new Ticket(serverTime, timeToLive, activationDate, expirationDate, userId, deviceId, blocked);
        }
    }
}