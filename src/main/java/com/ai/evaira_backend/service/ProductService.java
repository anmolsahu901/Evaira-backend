package com.ai.evaira_backend.service;

import com.ai.evaira_backend.dto.ProductDto;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> saveExternalProducts(List<ProductDto> products) {
        log.info("Storing the products");
        return products.stream()
                .map(this::mapAndSave)
                .toList();
    }

    private Product mapAndSave(ProductDto dto) {
        Product product = productRepository
                .findByExternalId(String.valueOf(dto.getId()))
                .orElse(new Product());

        product.setExternalId(String.valueOf(dto.getId()));
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        product.setDeeplinkUrl(dto.getDeeplinkUrl());
        product.setRating(dto.getRating());

        return productRepository.save(product);
    }

    public List<Product> getAllFromDb() {
        return productRepository.findAll();
    }

    public String getProductDeepLinkById(Long productId){
        return productRepository.findDeeplinkById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}


