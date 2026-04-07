package com.ai.evaira_backend.dto;

import java.util.List;

public class DiscoverResponse {

    private List<ProductDto> recommended;
    private List<ProductDto> trending;
    private List<ProductDto> newArrivals;
    private List<ProductDto> seasonalSale;

    public List<ProductDto> getRecommended() {
        return recommended;
    }

    public void setRecommended(List<ProductDto> recommended) {
        this.recommended = recommended;
    }

    public List<ProductDto> getTrending() {
        return trending;
    }

    public void setTrending(List<ProductDto> trending) {
        this.trending = trending;
    }

    public List<ProductDto> getNewArrivals() {
        return newArrivals;
    }

    public void setNewArrivals(List<ProductDto> newArrivals) {
        this.newArrivals = newArrivals;
    }

    public List<ProductDto> getSeasonalSale() {
        return seasonalSale;
    }

    public void setSeasonalSale(List<ProductDto> seasonalSale) {
        this.seasonalSale = seasonalSale;
    }
// constructor, getters
}
