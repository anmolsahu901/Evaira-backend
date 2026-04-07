package com.ai.evaira_backend.controller;


import com.ai.evaira_backend.dto.DiscoverResponse;
import com.ai.evaira_backend.dto.ProductDto;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.security.SecurityUtil;
import com.ai.evaira_backend.service.ProductService;
import com.ai.evaira_backend.service.RecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/discover")
public class DiscoverController {

    private static final Logger log = LoggerFactory.getLogger(DiscoverController.class);

    private final RecommendationService recommendationService;
    private final ProductService productService;

    public DiscoverController(RecommendationService recommendationServiceservice, ProductService productService) {
        this.recommendationService = recommendationServiceservice;
        this.productService = productService;
    }



    @PostMapping("/getData")
    public ResponseEntity<DiscoverResponse> getRecommendations() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<ProductDto> recommendedProducts = recommendationService.getRecommendedProducts(userId);
        List<ProductDto> newArrivalProducts = recommendationService.getNewArrivals();
        List<ProductDto> trendingProducts = recommendationService.getTrendingProducts();
        List<ProductDto> seasonalSaleProduct = recommendationService.getSeasonalSale();

        DiscoverResponse discoverResponse = new DiscoverResponse();
        discoverResponse.setRecommended(recommendedProducts);
        discoverResponse.setNewArrivals(newArrivalProducts);
        discoverResponse.setSeasonalSale(seasonalSaleProduct);
        discoverResponse.setTrending(trendingProducts);

        return ResponseEntity.ok(discoverResponse);
    }

}
