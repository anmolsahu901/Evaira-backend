package com.ai.evaira_backend.dto;

import com.ai.evaira_backend.dto.enums.BudgetRange;
import com.ai.evaira_backend.dto.enums.OccasionTag;
import com.ai.evaira_backend.dto.enums.ProductColor;
import lombok.Data;

import java.util.List;

public class UserRequest {

    private String gender;
    private BudgetRange budget;
    private String fitType;
    private List<OccasionTag> occasion;
    private String styleVibe;
    private ProductColor color;

    // Constructors
    public UserRequest() {}

    public UserRequest(String gender, String size, BudgetRange budget,
                       String fitType, List<OccasionTag> occasion,
                       String styleVibe, ProductColor color) {
        this.gender = gender;
        this.budget = budget;
        this.fitType = fitType;
        this.occasion = occasion;
        this.styleVibe = styleVibe;
        this.color = color;
    }

    // Getters
    public String getGender() { return gender; }
    public BudgetRange getBudget() { return budget; }
    public String getFitType() { return fitType; }
    public List<OccasionTag> getOccasion() { return occasion; }
    public String getStyleVibe() { return styleVibe; }
    public ProductColor getColor() { return color; }

    // Setters (optional; you can skip if only receiving from JSON)
    public void setGender(String gender) { this.gender = gender; }
    public void setBudget(BudgetRange budget) { this.budget = budget; }
    public void setFitType(String fitType) { this.fitType = fitType; }
    public void setOccasion(List<OccasionTag> occasion) { this.occasion = occasion; }
    public void setStyleVibe(String styleVibe) { this.styleVibe = styleVibe; }
    public void setColor(ProductColor color) { this.color = color; }
}