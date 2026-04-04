package com.ai.evaira_backend.dto.enums;


public enum PriceBucket {
    BUDGET(400,800),
    VALUE(800, 1600),
    MIDRANGE(1000, 2500),
    PREMIUM(2500, 10000);

    private final int min;
    private final int max;

    PriceBucket(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
