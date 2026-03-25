package com.ai.evaira_backend.service;

import com.ai.evaira_backend.dto.ProductDto;
import com.ai.evaira_backend.dto.enums.ProductColor;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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

    @Transactional
    public List<Product> saveExternalProducts(List<ProductDto> products) {

        log.info("Storing {} products", products.size());

        List<Product> entities = products.stream()
                .map(this::mapToEntity)
                .toList();

        return productRepository.saveAll(entities);
    }

    private Product mapToEntity(ProductDto dto) {

        Product product = productRepository
                .findByExternalId(dto.getExternalId())
                .orElse(new Product());

        product.setExternalId(dto.getExternalId());
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setSubCategory(dto.getSubCategory());
        product.setGender(dto.getGender());
        product.setPrimaryColor(dto.getPrimaryColor());
        product.setFitType(dto.getFitType());
        product.setFabric(dto.getFabric());
        product.setStyleType(dto.getStyleType());
        product.setSeason(dto.getSeason());
        product.setPriceBucket(dto.getPriceBucket());
        product.setColor(ProductColor.fromValue(dto.getPrimaryColor()));
        product.setImageUrl(dto.getImageUrl());
        product.setDeeplinkUrl(dto.getDeeplinkUrl());
        product.setRating(dto.getRating());
        product.setLikesCount(
                (long) (dto.getLikesCount() != null ? dto.getLikesCount() : 0)
        );

        product.setOccasionTags(dto.getOccasionTags());
        product.setColorFamily(dto.getColorFamily());
        product.setStyleVibe(dto.getStyleVibe());

        return product;
    }

    public List<Product> getAllFromDb() {
        return productRepository.findAll();
    }

    public String getProductDeepLinkById(Long productId){
        return productRepository.findDeeplinkById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}


