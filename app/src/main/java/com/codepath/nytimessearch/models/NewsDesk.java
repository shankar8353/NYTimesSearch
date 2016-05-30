package com.codepath.nytimessearch.models;

/**
 * Created by ssunda1 on 5/29/16.
 */
public enum NewsDesk {
    ARTS("Arts"),
    BUSINESS("Business"),
    FASHION_STYLE("Fashion & Style"),
    SPORTS("Sports"),
    TECHNOLOGY("Technology");

    private String label;

    NewsDesk(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
