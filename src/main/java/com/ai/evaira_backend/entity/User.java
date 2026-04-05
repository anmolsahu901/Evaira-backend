package com.ai.evaira_backend.entity;


import com.ai.evaira_backend.dto.enums.*;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;


import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<StyleVibe> styleVibes = new ArrayList<>();

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<FitType> fitTypes = new ArrayList<>();

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<OccasionTag> preferredOccasions = new ArrayList<>();

    // Color preferences
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<String> favoriteColors = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PriceBucket priceBucket;

    // unused properties
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String location; // pincode

    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    private FaceShape faceShape;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<String> dislikedColors; //optional

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<OccasionTag> avoidOccasions; //optional

    private String name;
    private Integer age;


    public PriceBucket getPriceBucket() {
        return priceBucket;
    }

    public void setPriceBucket(PriceBucket priceBucket) {
        this.priceBucket = priceBucket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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


}


