package com.ai.evaira_backend.dto;

import com.ai.evaira_backend.dto.enums.FitType;
import com.ai.evaira_backend.dto.enums.OccasionTag;
import com.ai.evaira_backend.dto.enums.PriceBucket;
import com.ai.evaira_backend.dto.enums.StyleVibe;
import lombok.Data;

import java.util.List;

@Data
// For JSON from https://fakestoreapi.com/products
public class ProductDto {

    private String externalId;
    private String title;
    private String description;
    private String imageUrl;
    private String deeplinkUrl;
    private Double price;
    private Double rating;
    private Long likesCount = 0L;
    private String gender;
    private String category;
    private String subCategory;
    private String primaryColor;
    private FitType fitType;
    private String fabric;
    private String styleType;
    private String season;
    private PriceBucket priceBucket;
    private List<OccasionTag> occasionTags;
    private List<StyleVibe> styleVibe;
    private List<String> colorFamily;


    public List<String> getColorFamily() {
        return colorFamily;
    }

    public void setColorFamily(List<String> colorFamily) {
        this.colorFamily = colorFamily;
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

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
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
}
