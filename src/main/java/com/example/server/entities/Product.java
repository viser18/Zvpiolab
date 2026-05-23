package com.example.server.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked = false;

    public Product() {}

    public Product(Long id, String name, Boolean isBlocked) {
        this.id = id;
        this.name = name;
        this.isBlocked = isBlocked;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public Boolean getIsBlocked() { return isBlocked; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setIsBlocked(Boolean isBlocked) { this.isBlocked = isBlocked; }
}