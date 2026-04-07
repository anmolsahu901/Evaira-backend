package com.ai.evaira_backend.dto;

import com.ai.evaira_backend.entity.Product;
import lombok.Data;

@Data
public class ProductScore {
    private Product product;
    private double score;

    public ProductScore(Product product, double score) {
        this.product = product;
        this.score = score;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

