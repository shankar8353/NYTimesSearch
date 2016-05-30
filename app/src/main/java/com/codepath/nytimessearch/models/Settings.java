package com.codepath.nytimessearch.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ssunda1 on 5/29/16.
 */
@Parcel
public class Settings {
    Date beginDate;
    Date endDate;
    SortOrder sortOrder;
    Map<NewsDesk, Boolean> enabled;

    public Settings() {
        sortOrder = SortOrder.NEWEST;
        enabled = new HashMap<>();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void enable(NewsDesk newsdesk, boolean value) {
        enabled.put(newsdesk, value);
    }

    public List<NewsDesk> getEnabledNewsDesks() {
        List<NewsDesk> values = new ArrayList<>();
        for (Map.Entry<NewsDesk, Boolean> e : enabled.entrySet()) {
            if (e.getValue()) {
                values.add(e.getKey());
            }
        }
        return values;
    }

    public boolean isEnabled(NewsDesk newsDesk) {
        Boolean value = enabled.get(newsDesk);
        return value == null ? false : value.booleanValue();
    }
}
