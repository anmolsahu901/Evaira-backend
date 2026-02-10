package com.ai.evaira_backend.controller;

import com.ai.evaira_backend.dto.ProductDto;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String FAKESTORE_URL = "https://fakestoreapi.com/products";

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1) Fetch from external API in controller, pass list to service to save
    @PostMapping("/import")
    public ResponseEntity<List<Product>> importFromFakeStore() {
        log.info("Importing products");
        ProductDto[] externalArray =
                restTemplate.getForObject(FAKESTORE_URL, ProductDto[].class);

        if (externalArray == null) {
            return ResponseEntity.ok(List.of());
        }

        List<ProductDto> externalList = Arrays.asList(externalArray);
        List<Product> saved = productService.saveExternalProducts(externalList);
        return ResponseEntity.ok(saved);
    }

    // 2) Read products from your DB
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAllFromDb());
    }
}

