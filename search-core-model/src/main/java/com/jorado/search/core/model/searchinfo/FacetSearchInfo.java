package com.jorado.search.core.model.searchinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FacetSearchInfo implements Serializable {

    private List<String> query;
    private List<String> field;
    private int limit = -1;

    public FacetSearchInfo() {
        this.query = new ArrayList<>();
        this.field = new ArrayList<>();
    }

    public List<String> getQuery() {
        return query;
    }

    public void setQuery(List<String> query) {
        this.query = query;
    }

    public List<String> getField() {
        return field;
    }

    public void setField(List<String> field) {
        this.field = field;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "FacetSearchInfo{" +
                "query=" + query +
                ", field=" + field +
                ", limit=" + limit +
                '}';
    }
}
