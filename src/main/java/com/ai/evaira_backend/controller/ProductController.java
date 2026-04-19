package com.ai.evaira_backend.controller;

import com.ai.evaira_backend.dto.ProductDto;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.service.ProductService;
import com.ai.evaira_backend.service.RecommendationService;
import com.ai.evaira_backend.service.UserProductActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final RecommendationService recommendationServiceservice;
    private final ProductService productService;
    private final UserProductActionService actionService;

    public ProductController(RecommendationService recommendationService, ProductService productService, UserProductActionService actionService) {
        this.recommendationServiceservice = recommendationService;
        this.productService = productService;
        this.actionService = actionService;
    }

    // these endpoint are used for admin only not for user

    @GetMapping("/getall")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAllFromDb());
    }

    @PostMapping("/import")
    public ResponseEntity<List<Product>> fetchProductFromJson(@RequestBody List<ProductDto> products) {

        List<Product> saved = productService.saveExternalProducts(products);
        return ResponseEntity.ok(saved);

    }



}

