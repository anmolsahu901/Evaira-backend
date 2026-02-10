package com.ai.evaira_backend.dto;


// request DTO from frontend
public class UserProductActionRequest {

    private Long productId;
    private ProductActionType actionType;

    // optional: you can pass userId here or get it from JWT/auth context
    private Long userId;
    private String metadata;
    // getters & setters


    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
