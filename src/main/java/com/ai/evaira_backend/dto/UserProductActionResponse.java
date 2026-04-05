package com.ai.evaira_backend.dto;



import com.ai.evaira_backend.dto.enums.ProductActionType;

import java.time.Instant;

public class UserProductActionResponse {

    private Long id;
    private Long userId;
    private Long productId;
    private ProductActionType actionType;
    private Instant createdAt;
    private String deeplinkUrl;  //  return deeplink for OPEN/SHARE
    private String metadata;
    // getters & setters


    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getDeeplinkUrl() {
        return deeplinkUrl;
    }

    public void setDeeplinkUrl(String deeplinkUrl) {
        this.deeplinkUrl = deeplinkUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProductActionType getActionType() {
        return actionType;
    }

    public void setActionType(ProductActionType actionType) {
        this.actionType = actionType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}