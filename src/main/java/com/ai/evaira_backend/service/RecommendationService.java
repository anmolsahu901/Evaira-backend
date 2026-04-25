package com.ai.evaira_backend.service;


import com.ai.evaira_backend.dto.ProductDto;
import com.ai.evaira_backend.dto.ProductScore;
import com.ai.evaira_backend.dto.enums.FitType;
import com.ai.evaira_backend.dto.enums.OccasionTag;
import com.ai.evaira_backend.dto.enums.ProductActionType;
import com.ai.evaira_backend.dto.enums.StyleVibe;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.entity.UserProductAction;
import com.ai.evaira_backend.repository.ProductRepository;
import com.ai.evaira_backend.repository.UserProductActionRepository;
import com.ai.evaira_backend.repository.UserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final UserProductActionRepository actionRepo;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    Pageable pageable = PageRequest.of(0, 200);

    public RecommendationService(UserProductActionRepository actionRepo, UserRepository userRepository, ProductRepository productRepository) {
        this.actionRepo = actionRepo;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // recommendation logic
    public List<Product> getRecommendedProducts(Long userId) {

        User user = userRepository.findById(userId).orElseThrow();

        List<Product> seenProducts = actionRepo.findProductsByUserIdAndActionTypes(userId, ProductActionType.SEEN);
        Set<Long> seenProductIds = seenProducts.stream().map(Product::getId).collect(Collectors.toSet());

        List<Product> allProducts = productRepository.findAll();

        List<ProductScore> scoredProducts = new ArrayList<>();
        BehaviorProfile behaviorProfile = buildBehaviorProfile(userId);

        for (Product product : allProducts) {
            if (seenProductIds.contains(product.getId())) {
                continue;
            }

            double score = calculateScore(user, product, behaviorProfile);

            scoredProducts.add(new ProductScore(product, score));
        }

        // 🔥 Sort by score
        scoredProducts.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));

        // 🔥 Take top 20
        return scoredProducts.stream()
                .limit(20)
                .map(ps -> convertToDto(ps.getProduct()))
                .toList();
    }

    public List<Product> getSmartHomeFeed(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        // 1. Get seen products to exclude them
        List<Product> seenProducts = actionRepo.findProductsByUserIdAndActionTypes(userId, ProductActionType.SEEN);
        Set<Long> seenProductIds = seenProducts.stream().map(Product::getId).collect(Collectors.toSet());

        // 2. Filter out seen products
        List<Product> allProducts = productRepository.findAll();
        List<Product> unseenProducts = new ArrayList<>();
        for (Product product : allProducts) {
            if (!seenProductIds.contains(product.getId())) {
                unseenProducts.add(product);
            }
        }

        // 3. Recommended (50% = 10 items)
        List<ProductScore> scoredProducts = new ArrayList<>();
        BehaviorProfile behaviorProfile = buildBehaviorProfile(userId);
        
        for (Product product : unseenProducts) {
            double score = calculateScore(user, product, behaviorProfile);
            scoredProducts.add(new ProductScore(product, score));
        }
        scoredProducts.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
        
        List<Product> recommended = scoredProducts.stream()
                .limit(30)
                .map(ProductScore::getProduct)
                .toList();
        
        Set<Long> usedIds = recommended.stream().map(Product::getId).collect(Collectors.toSet());

        // 4. New/Unseen (25% = 5 items)
        List<Product> newArrivals = unseenProducts.stream()
                .filter(p -> !usedIds.contains(p.getId()))
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(10)
                .toList();

        usedIds.addAll(newArrivals.stream().map(Product::getId).collect(Collectors.toSet()));

        // 5. Random (25% = 5 items)
        List<Product> remaining = unseenProducts.stream()
                .filter(p -> !usedIds.contains(p.getId()))
                .collect(Collectors.toList());
        java.util.Collections.shuffle(remaining);
        List<Product> randomProducts = remaining.stream().limit(10).toList();

        // Combine them all
        List<Product> smartFeed = new ArrayList<>();
        smartFeed.addAll(recommended);
        smartFeed.addAll(newArrivals);
        smartFeed.addAll(randomProducts);

        // Shuffle the feed so it's a nice mix
        java.util.Collections.shuffle(smartFeed);

        return smartFeed.stream().map(this::convertToDto).toList();
    }



    // 🔥 MAIN METHOD
    public double calculateScore(User user, Product product, BehaviorProfile behaviorProfile) {
        double profileScore = calculateProfileScore(user, product);
        double behaviorScore = calculateBehaviorScore(product, behaviorProfile);

        return profileScore + behaviorScore;
    }

    private double calculateProfileScore(User user, Product product) {
        double score = 0;

        // STYLE VIBE MATCH
        if (product.getStyleVibe() != null && user.getStyleVibes() != null) {
            for (StyleVibe vibe : product.getStyleVibe()) {
                if (user.getStyleVibes().contains(vibe)) {
                    score += 3;
                    break;
                }
            }
        }

        // FIT TYPE MATCH
        if (product.getFitType() != null && user.getFitTypes() != null) {
            if (user.getFitTypes().contains(product.getFitType())) {
                score += 2;
            }
        }

        // OCCASION MATCH
        if (product.getOccasionTags() != null && user.getPreferredOccasions() != null) {
            for (OccasionTag tag : product.getOccasionTags()) {
                if (user.getPreferredOccasions().contains(tag)) {
                    score += 2;
                    break;
                }
            }
        }

        // PRICE MATCH
        if (product.getPriceBucket() != null && user.getPriceBucket() != null) {
            if (product.getPriceBucket().equals(user.getPriceBucket())) {
                score += 1;
            }
        }

        // COLOR MATCH
        if (product.getColorFamily() != null && user.getFavoriteColors() != null) {
            for (String color : product.getColorFamily()) {
                if (user.getFavoriteColors().contains(color)) {
                    score += 1;
                    break;
                }
            }
        }

        return score;
    }

    private static class BehaviorProfile {
        Map<StyleVibe, Integer> styleScore = new HashMap<>();
        Map<FitType, Integer> fitScore = new HashMap<>();
        Map<OccasionTag, Integer> occasionScore = new HashMap<>();
    }

    private BehaviorProfile buildBehaviorProfile(Long userId) {
        List<UserProductAction> actions = actionRepo.findRecentActions(userId, pageable);
        BehaviorProfile profile = new BehaviorProfile();

        for (UserProductAction action : actions) {
            Product p = action.getProduct();
            int weight = getWeight(action.getActionType());

            // STYLE
            if (p.getStyleVibe() != null) {
                for (StyleVibe vibe : p.getStyleVibe()) {
                    profile.styleScore.put(vibe, profile.styleScore.getOrDefault(vibe, 0) + weight);
                }
            }

            // FIT
            if (p.getFitType() != null) {
                profile.fitScore.put(p.getFitType(), profile.fitScore.getOrDefault(p.getFitType(), 0) + weight);
            }

            // OCCASION
            if (p.getOccasionTags() != null) {
                for (OccasionTag tag : p.getOccasionTags()) {
                    profile.occasionScore.put(tag, profile.occasionScore.getOrDefault(tag, 0) + weight);
                }
            }
        }
        return profile;
    }

    private double calculateBehaviorScore(Product product, BehaviorProfile profile) {
        double score = 0;

        // APPLY TO CURRENT PRODUCT

        // STYLE
        if (product.getStyleVibe() != null) {
            for (StyleVibe vibe : product.getStyleVibe()) {
                score += profile.styleScore.getOrDefault(vibe, 0);
            }
        }

        // FIT
        if (product.getFitType() != null) {
            score += profile.fitScore.getOrDefault(product.getFitType(), 0);
        }

        // OCCASION
        if (product.getOccasionTags() != null) {
            for (OccasionTag tag : product.getOccasionTags()) {
                score += profile.occasionScore.getOrDefault(tag, 0);
            }
        }

        return score;
    }

    private int getWeight(ProductActionType actionType) {
        return switch (actionType) {
            case LIKE -> 3;
            case SAVE -> 4;
            case SHARE -> 5;
            case OPEN -> 1;
            case DISLIKE -> -3;
            case SEEN -> 0;
            case UNLIKE, UNSAVE -> -2;
        };
    }


    
private Product convertToDto(Product product) {
    // ProductDto dto = new ProductDto();
    // BeanUtils.copyProperties(product, dto);
    return product;
}
//    private ProductDto convertToDto1(Product product) {
//
//        ProductDto dto = new ProductDto();
//        dto.setId(product.getId());
//        dto.setBrand(product.getBrand());
//        dto.setExternalId(product.getExternalId());
//        dto.setTitle(product.getTitle());
//        dto.setDescription(product.getDescription());
//        dto.setImageUrl(product.getImageUrl());
//        dto.setDeeplinkUrl(product.getDeeplinkUrl());
//        dto.setPrice(product.getPrice());
//        dto.setRating(product.getRating());
//        dto.setLikesCount(product.getLikesCount());
//        dto.setGender(product.getGender());
//        dto.setCategory(product.getCategory());
//        dto.setSubCategory(product.getSubCategory());
//        dto.setPrimaryColor(product.getPrimaryColor());
//        dto.setFitType(product.getFitType());
//        dto.setFabric(product.getFabric());
//        dto.setStyleType(product.getStyleType());
//        dto.setSeason(product.getSeason());
//        dto.setPriceBucket(product.getPriceBucket());
//        dto.setOccasionTags(product.getOccasionTags());
//        dto.setStyleVibe(product.getStyleVibe());
//        dto.setColorFamily(product.getColorFamily());
//
//        return dto;
//    }

    // new arrivals
    public List<Product> getNewArrivals() {
        List<Product> products = productRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, 20));

        return products.stream()
                .toList();
    }

    // trending products
    public List<Product> getTrendingProducts() {
        List<Product> products = productRepository.findTrendingProducts();

        return products.stream()
                
                .toList();
    }

    // Seasonal Sale
    public List<Product> getSeasonalSale() {
        return productRepository.findByPriceLessThan(1000.0,PageRequest.of(0, 20))
                .stream()
            
                .toList();
    }



}
    

