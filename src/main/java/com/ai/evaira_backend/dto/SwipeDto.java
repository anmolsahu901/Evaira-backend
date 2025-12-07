package com.ai.evaira_backend.dto;

import lombok.Data;

@Data
public class SwipeDto {
    private Long productId;
    private String direction;  // "LEFT" or "RIGHT"

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
// Getters and setters
}

