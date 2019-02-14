package com.jorado.search.core.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class FacetResult implements Serializable {
    private Map<String, Integer> facet_queries;
    private Map<String, Map<String, Long>> facet_fields;

    public FacetResult() {

        this.facet_queries = new LinkedHashMap<>();
        this.facet_fields = new LinkedHashMap<>();
    }

    public Map<String, Integer> getFacet_queries() {
        return facet_queries;
    }

    public void setFacet_queries(Map<String, Integer> facet_queries) {
        this.facet_queries = facet_queries;
    }

    public Map<String, Map<String, Long>> getFacet_fields() {
        return facet_fields;
    }

    public void setFacet_fields(Map<String, Map<String, Long>> facet_fields) {
        this.facet_fields = facet_fields;
    }
}
