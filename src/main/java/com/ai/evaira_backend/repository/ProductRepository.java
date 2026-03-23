package com.ai.evaira_backend.repository;

import com.ai.evaira_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can add custom queries here as needed.
    Optional<Product> findByExternalId(String externalId);

    @Query("select p.deeplinkUrl from Product p where p.id = :id")
    Optional<String> findDeeplinkById(@Param("id") Long id);

    List<Product> findByGender(String gender);

}
