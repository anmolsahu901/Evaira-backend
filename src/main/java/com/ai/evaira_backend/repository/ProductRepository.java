package com.ai.evaira_backend.repository;

import com.ai.evaira_backend.entity.Product;
import org.springframework.data.domain.Pageable;
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

    //Optional<Product> getAllProduct();

    @Query("SELECT p FROM Product p ORDER BY RANDOM()")
    List<Product> findAllRandom();

    List<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query(value = """
    SELECT p.*
    FROM user_product_actions a
    JOIN products p ON p.id = a.product_id
    WHERE a.created_at > now() - interval '7 days'
    GROUP BY p.id
    ORDER BY 
        SUM(
            CASE 
                WHEN a.action_type = 'LIKE' THEN 2
                WHEN a.action_type = 'SAVE' THEN 3
                WHEN a.action_type = 'SHARE' THEN 4
                WHEN a.action_type = 'OPEN' THEN 1
                WHEN a.action_type = 'DISLIKE' THEN -2
                ELSE 0
            END
        ) DESC
    LIMIT 20
""", nativeQuery = true)
    List<Product> findTrendingProducts();


    List<Product> findByPriceLessThan(Double price, Pageable pageable);

}