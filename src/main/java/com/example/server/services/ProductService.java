package com.example.server.services;

import com.example.server.entities.Product;
import com.example.server.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product createProduct(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("Product name is required");
        }
        if (productRepository.existsByName(name)) {
            throw new RuntimeException("Product with name '" + name + "' already exists");
        }

        // Создаём продукт без builder
        Product product = new Product();
        product.setName(name);
        product.setIsBlocked(false);

        return productRepository.save(product);
    }

    public Product getProductOrFail(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product blockProduct(Long id, boolean blocked) {
        Product product = getProductOrFail(id);
        product.setIsBlocked(blocked);
        return productRepository.save(product);
    }
}