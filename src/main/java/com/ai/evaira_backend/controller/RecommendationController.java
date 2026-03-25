package com.ai.evaira_backend.controller;

import com.ai.evaira_backend.dto.UserRequest;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/products")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    /**
     * POST /api/products/recommend
     * Accepts user preferences (UserRequest) and returns a list of recommended products.
     */
    @PostMapping("/recommend")
    public ResponseEntity<List<Product>> getRecommendations(
            @RequestBody UserRequest request) {

        List<Product> recommendations = service.recommend(request);
        return ResponseEntity.ok(recommendations);
    }
}
