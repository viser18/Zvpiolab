package com.example.server.models;

public class AuthenticationResponse {
    private String email;
    private String accessToken;
    private String refreshToken;

    public AuthenticationResponse() {}

    public AuthenticationResponse(String email, String accessToken, String refreshToken) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getEmail() { return email; }
    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public void setEmail(String email) { this.email = email; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}