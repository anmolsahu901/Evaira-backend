package com.ai.evaira_backend.repository;

import com.ai.evaira_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can add custom queries here as needed.
    Optional<Product> findByExternalId(String externalId);
}
