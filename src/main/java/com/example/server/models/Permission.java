package com.example.server.models;

public enum Permission {
    READ("read"),
    MODIFY("modify");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}