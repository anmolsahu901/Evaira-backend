package com.ai.evaira_backend.dto;

import lombok.Data;

@Data
// For JSON from https://fakestoreapi.com/products
public class ProductDto {
    private Long id;
    private String title; //name
    private Double price;
    private String description;
    private String category;
    private String imageUrl;
    private String deeplinkUrl;
    private Double rating;
    private Long likesCount;

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

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

//    public static class Rating {
//        private Double rate;
//        private Integer count;
//        // getters/setters
//
//        public Double getRate() {
//            return rate;
//        }
//
//        public void setRate(Double rate) {
//            this.rate = rate;
//        }
//
//        public Integer getCount() {
//            return count;
//        }
//
//        public void setCount(Integer count) {
//            this.count = count;
//        }
//    }
    // getters/setters
}
