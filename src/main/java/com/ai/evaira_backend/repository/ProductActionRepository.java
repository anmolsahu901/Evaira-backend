package com.ai.evaira_backend.repository;

import com.ai.evaira_backend.entity.ProductAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductActionRepository extends JpaRepository<ProductAction, Long> {
    List<ProductAction> findByUserId(Long userId);
}

