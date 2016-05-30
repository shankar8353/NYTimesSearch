package com.codepath.nytimessearch.models;

/**
 * Created by ssunda1 on 5/29/16.
 */
public enum SortOrder {
    NEWEST("Newest"),
    OLDEST("Oldest");

    private String label;

    SortOrder(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
