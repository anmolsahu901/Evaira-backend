package com.ai.evaira_backend.dto;


import com.ai.evaira_backend.dto.enums.*;
import lombok.Data;

import java.util.List;

@Data
public class ProfileDto {
    private String name;

    private Integer age;

    private Gender gender;

    private String location; // pincode

    // Body / face / physical attributes
    private BodyType bodyType;      // rectangle, oval, athletic
    private FaceShape faceShape;     // optional for accessories suggestions

    // Style preferences
    private List<StyleVibe> styleVibes;

    private List<FitType> fitTypes;

    private List<OccasionTag> preferredOccasions;

    private List<OccasionTag> avoidOccasions; //optional

    // Color preferences
    private List<String> favoriteColors;

    private List<String> dislikedColors; //optional

    // Budget preference
    private Integer minBudget;

    private Integer maxBudget; // optional

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public FaceShape getFaceShape() {
        return faceShape;
    }

    public void setFaceShape(FaceShape faceShape) {
        this.faceShape = faceShape;
    }

    public List<StyleVibe> getStyleVibes() {
        return styleVibes;
    }

    public void setStyleVibes(List<StyleVibe> styleVibes) {
        this.styleVibes = styleVibes;
    }

    public List<FitType> getFitTypes() {
        return fitTypes;
    }

    public void setFitTypes(List<FitType> fitTypes) {
        this.fitTypes = fitTypes;
    }

    public List<OccasionTag> getPreferredOccasions() {
        return preferredOccasions;
    }

    public void setPreferredOccasions(List<OccasionTag> preferredOccasions) {
        this.preferredOccasions = preferredOccasions;
    }

    public List<OccasionTag> getAvoidOccasions() {
        return avoidOccasions;
    }

    public void setAvoidOccasions(List<OccasionTag> avoidOccasions) {
        this.avoidOccasions = avoidOccasions;
    }

    public List<String> getFavoriteColors() {
        return favoriteColors;
    }

    public void setFavoriteColors(List<String> favoriteColors) {
        this.favoriteColors = favoriteColors;
    }

    public List<String> getDislikedColors() {
        return dislikedColors;
    }

    public void setDislikedColors(List<String> dislikedColors) {
        this.dislikedColors = dislikedColors;
    }

    public Integer getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(Integer minBudget) {
        this.minBudget = minBudget;
    }

    public Integer getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(Integer maxBudget) {
        this.maxBudget = maxBudget;
    }
}

