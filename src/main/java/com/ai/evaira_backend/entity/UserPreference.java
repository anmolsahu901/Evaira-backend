package com.ai.evaira_backend.entity;

import jakarta.persistence.*;

import lombok.Data;

@Data
public class UserPreference {


    private Long id;
    private String type;   // "OCCASION", "COLOR", etc.
    private String value;  // "ethnic", "party", "casual", "black"

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
// getters/setters
}

