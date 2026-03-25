package com.ai.evaira_backend.service;

import com.ai.evaira_backend.dto.UserRequest;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    private final ProductRepository repo;

    public RecommendationService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> recommend(UserRequest user) {
        // 1. Pre‑filter by gender (from DB)
        List<Product> products = repo.findByGender(user.getGender());

        // 2. Filter in‑memory by:
        // - size,
        // - budget,
        // - assign score,
        // - sort by score desc,
        // - limit top 20
        return products.stream()
                .filter(p -> filterByBudget(p, user))                                   // budget filter
                .map(p -> {
                    p.setScore(calculateScore(p, user));                               // calculate & attach score
                    return p;
                })
                .sorted((a, b) -> Integer.compare(b.getScore(), a.getScore()))         // highest score first
                .limit(20)
                .toList();
    }

    private boolean filterByBudget(Product p, UserRequest user) {
        if (user.getBudget() == null) {
            return true;  // no budget constraint
        }
        double price = p.getPrice();
        return price >= user.getBudget().getMin() && price <= user.getBudget().getMax();
    }

    private int calculateScore(Product p, UserRequest u) {
        int score = 0;

        if (p.getFitType() != null && u.getFitType() != null
                && p.getFitType().equalsIgnoreCase(u.getFitType())) {
            score += 40;
        }

        if (p.getOccasionTags() != null && u.getOccasion() != null
                && p.getOccasionTags().stream().anyMatch(tag -> u.getOccasion().contains(tag))) {
            score += 30;
        }

        if (p.getStyleVibe() != null && u.getStyleVibe() != null
                && p.getStyleVibe().stream().anyMatch(vibe -> vibe.name().equalsIgnoreCase(u.getStyleVibe()))) {
            score += 20;
        }

        if (p.getColor() != null && u.getColor() != null
                && p.getColor().equals(u.getColor())) {
            score += 10;
        }

        return score;
    }
}