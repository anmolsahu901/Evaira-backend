package com.ai.evaira_backend.dto.enums;

public enum BudgetRange {
    LOW(0, 999),
    MEDIUM(1000, 2999),
    HIGH(3000, 10000);

    private final int min;
    private final int max;

    BudgetRange(int min, int max) {
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
