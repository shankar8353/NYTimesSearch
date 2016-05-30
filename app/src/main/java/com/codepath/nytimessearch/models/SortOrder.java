package com.codepath.nytimessearch.models;

/**
 * Created by ssunda1 on 5/29/16.
 */
public enum SortOrder {
    NEWEST("Newest", "newest"),
    OLDEST("Oldest", "oldest");

    private String label;
    private String order;

    SortOrder(String label, String order) {
        this.label = label;
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return label;
    }
}
