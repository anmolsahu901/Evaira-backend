package com.ai.evaira_backend.entity;

import com.ai.evaira_backend.dto.enums.FitType;
import com.ai.evaira_backend.dto.enums.OccasionTag;
import com.ai.evaira_backend.dto.enums.PriceBucket;
import com.ai.evaira_backend.dto.enums.StyleVibe;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // DB ID

    @Column(nullable = false)
    private String externalId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(length = 500)
    private String imageUrl;
    @Column(length = 500)
    private String deeplinkUrl;

    private Double price;
    private Double rating;

    private String gender;
    private String category;
    private String subCategory;
    private String primaryColor;
    private FitType fitType;
    private String fabric;
    private String styleType;
    private String season;

    @Enumerated(EnumType.STRING)
    private PriceBucket priceBucket;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Long likesCount =0L;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<OccasionTag> occasionTags = new ArrayList<>();

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<String> colorFamily = new ArrayList<>();

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<StyleVibe> styleVibe = new ArrayList<>();

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<OccasionTag> getOccasionTags() {
        return occasionTags;
    }

    public void setOccasionTags(List<OccasionTag> occasionTags) {
        this.occasionTags = occasionTags;
    }

    public List<StyleVibe> getStyleVibe() {
        return styleVibe;
    }

    public void setStyleVibe(List<StyleVibe> styleVibe) {
        this.styleVibe = styleVibe;
    }

    public List<String> getColorFamily() {
        return colorFamily;
    }

    public void setColorFamily(List<String> colorFamily) {
        this.colorFamily = colorFamily;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDeeplinkUrl() {
        return deeplinkUrl;
    }

    public void setDeeplinkUrl(String deeplinkUrl) {
        this.deeplinkUrl = deeplinkUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public FitType getFitType() {
        return fitType;
    }

    public void setFitType(FitType fitType) {
        this.fitType = fitType;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getStyleType() {
        return styleType;
    }

    public void setStyleType(String styleType) {
        this.styleType = styleType;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public PriceBucket getPriceBucket() {
        return priceBucket;
    }

    public void setPriceBucket(PriceBucket priceBucket) {
        this.priceBucket = priceBucket;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }
}


