package com.ai.evaira_backend.service;

import com.ai.evaira_backend.dto.ProductDto;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(ProductDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setPrice(dto.getPrice());
        product.setDeeplinkUrl(dto.getDeeplinkUrl());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}

