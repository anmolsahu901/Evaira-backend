package com.ai.evaira_backend.dto;

import java.util.List;

import com.ai.evaira_backend.entity.Product;

public class DiscoverResponse {

    private List<Product> recommended;
    private List<Product> trending;
    private List<Product> newArrivals;
    private List<Product> seasonalSale;

    public List<Product> getRecommended() {
        return recommended;
    }

    public void setRecommended(List<Product> recommended) {
        this.recommended = recommended;
    }

    public List<Product> getTrending() {
        return trending;
    }

    public void setTrending(List<Product> trending) {
        this.trending = trending;
    }

    public List<Product> getNewArrivals() {
        return newArrivals;
    }

    public void setNewArrivals(List<Product> newArrivals) {
        this.newArrivals = newArrivals;
    }

    public List<Product> getSeasonalSale() {
        return seasonalSale;
    }

    public void setSeasonalSale(List<Product> seasonalSale) {
        this.seasonalSale = seasonalSale;
    }
// constructor, getters
}
