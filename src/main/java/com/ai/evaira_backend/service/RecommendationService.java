package com.ai.evaira_backend.service;


import com.ai.evaira_backend.dto.enums.FitType;
import com.ai.evaira_backend.dto.enums.OccasionTag;
import com.ai.evaira_backend.dto.enums.ProductActionType;
import com.ai.evaira_backend.dto.enums.StyleVibe;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.entity.UserProductAction;
import com.ai.evaira_backend.repository.ProductRepository;
import com.ai.evaira_backend.repository.UserProductActionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecommendationService {

    private final UserProductActionRepository actionRepo;

    Pageable pageable = PageRequest.of(0, 200);

    public RecommendationService(UserProductActionRepository actionRepo) {
        this.actionRepo = actionRepo;
    }

    // 🔥 MAIN METHOD
    public double calculateScore(User user, Product product) {
        double profileScore = calculateProfileScore(user, product);
        double behaviorScore = calculateBehaviorScore(user, product);

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

    private double calculateBehaviorScore(User user, Product product) {

        // Fetch last 200 actions (limit for performance)
        List<UserProductAction> actions =
                actionRepo.findRecentActions(user.getId(),pageable);

        Map<StyleVibe, Integer> styleScore = new HashMap<>();
        Map<FitType, Integer> fitScore = new HashMap<>();
        Map<OccasionTag, Integer> occasionScore = new HashMap<>();

        for (UserProductAction action : actions) {

            Product p = action.getProduct();
            int weight = getWeight(action.getActionType());

            // STYLE
            if (p.getStyleVibe() != null) {
                for (StyleVibe vibe : p.getStyleVibe()) {
                    styleScore.put(vibe,
                            styleScore.getOrDefault(vibe, 0) + weight);
                }
            }

            // FIT
            if (p.getFitType() != null) {
                fitScore.put(p.getFitType(),
                        fitScore.getOrDefault(p.getFitType(), 0) + weight);
            }

            // OCCASION
            if (p.getOccasionTags() != null) {
                for (OccasionTag tag : p.getOccasionTags()) {
                    occasionScore.put(tag,
                            occasionScore.getOrDefault(tag, 0) + weight);
                }
            }
        }

        double score = 0;

        // APPLY TO CURRENT PRODUCT

        // STYLE
        if (product.getStyleVibe() != null) {
            for (StyleVibe vibe : product.getStyleVibe()) {
                score += styleScore.getOrDefault(vibe, 0);
            }
        }

        // FIT
        if (product.getFitType() != null) {
            score += fitScore.getOrDefault(product.getFitType(), 0);
        }

        // OCCASION
        if (product.getOccasionTags() != null) {
            for (OccasionTag tag : product.getOccasionTags()) {
                score += occasionScore.getOrDefault(tag, 0);
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
            case UNLIKE, UNSAVE -> -2;
        };
    }
}
    

