package com.ai.evaira_backend.controller;

import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.service.ProductService;
import com.ai.evaira_backend.service.RecommendationService;
import com.ai.evaira_backend.service.UserProductActionService;
import com.ai.evaira_backend.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/products")
public class HomeActivityController {

    private static final Logger log = LoggerFactory.getLogger(HomeActivityController.class);

    private final RecommendationService recommendationService;
    private final ProductService productService;

    public HomeActivityController(ProductService productService, UserProductActionService actionService, RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
        this.productService = productService;
    }

    @GetMapping("/getHomeFeedProducts")
    public ResponseEntity<List<Product>> getHomeFeedProducts() {
        Long userId = SecurityUtil.getCurrentUserId();
        log.info("Fetching Smart Home Feed for user: {}", userId);
        return ResponseEntity.ok(productService.getAllFromDb());
    }






}
