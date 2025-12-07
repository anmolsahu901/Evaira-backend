package com.ai.evaira_backend.dto;


import lombok.Data;

import java.util.List;

@Data
public class ProfileDto {
    private String name;
    private Integer age;
    private String gender;
    private String location; // pincode

    // Body / face / style info
    private String bodyType;          // e.g. "oval", "rectangle", "hourglass"
    private String faceShape;         // optional, if you need for accessories later

    // Style preferences
    private List<String> preferredOccasions; // e.g. ["ethnic", "indo-western", "party", "casual"]
    private List<String> avoidOccasions;     // optional: things they don't like

    private List<String> favoriteColors;     // e.g. ["black", "blue"]
    private List<String> dislikedColors;     // optional

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getFaceShape() {
        return faceShape;
    }

    public void setFaceShape(String faceShape) {
        this.faceShape = faceShape;
    }

    public List<String> getPreferredOccasions() {
        return preferredOccasions;
    }

    public void setPreferredOccasions(List<String> preferredOccasions) {
        this.preferredOccasions = preferredOccasions;
    }

    public List<String> getAvoidOccasions() {
        return avoidOccasions;
    }

    public void setAvoidOccasions(List<String> avoidOccasions) {
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

// getters and setters
}

