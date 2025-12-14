package com.ai.evaira_backend.service;

import com.ai.evaira_backend.dto.ProductActionType;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.entity.ProductAction;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.repository.ProductActionRepository;
import com.ai.evaira_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductActionService {

    private final ProductActionRepository actionRepository;
    private final ProductRepository productRepository;

    public ProductActionService(ProductActionRepository actionRepository,
                                ProductRepository productRepository) {
        this.actionRepository = actionRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void recordAction(User user, Long productId, ProductActionType type) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductAction action = new ProductAction();
        action.setUser(user);
        action.setProduct(product);
        action.setActionType(type);

        actionRepository.save(action);
    }
}

