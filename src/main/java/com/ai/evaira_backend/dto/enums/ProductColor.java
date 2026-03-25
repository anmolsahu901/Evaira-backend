package com.ai.evaira_backend.dto.enums;

public enum ProductColor {
    BLACK, WHITE, GREY, RED, BLUE, GREEN, YELLOW, ORANGE,
    PURPLE, PINK, BROWN, BEIGE, CREAM, MAROON, NAVY,
    OLIVE, TEAL, TURQUOISE, GOLD, SILVER, MULTICOLOR, OTHER, UNKNOWN;

    public static ProductColor fromValue(String value) {
        if (value == null) return UNKNOWN;

        try {
            return ProductColor.valueOf(value.trim().toUpperCase());
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}