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

    public Long getId() { return id; }
    public String getName() { return name; }
    public Boolean getIsBlocked() { return isBlocked; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setIsBlocked(Boolean isBlocked) { this.isBlocked = isBlocked; }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private Long id;
        private String name;
        private Boolean isBlocked = false;

        public ProductBuilder id(Long id) { this.id = id; return this; }
        public ProductBuilder name(String name) { this.name = name; return this; }
        public ProductBuilder isBlocked(Boolean isBlocked) { this.isBlocked = isBlocked; return this; }

        public Product build() {
            return new Product(id, name, isBlocked);
        }
    }
}