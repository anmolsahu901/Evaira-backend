package com.ai.evaira_backend.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ProductColor {
    BLACK, WHITE, GREY, RED, BLUE, GREEN, YELLOW, ORANGE, PURPLE, PINK,
    BROWN, BEIGE, CREAM, MAROON, NAVY, OLIVE, TEAL, TURQUOISE,
    GOLD, SILVER, MULTICOLOR, OTHER, UNKNOWN;

    @JsonCreator
    public static ProductColor fromValue(String value) {
        try {
            return ProductColor.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return UNKNOWN;
        }

    }
}