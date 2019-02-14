package com.jorado.search.core.model.solrresult;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FacetCounts {

    @JsonProperty("facet_queries")
    private FacetQueries facetQueries;

    @JsonProperty("facet_fields")
    private FacetFields facetFields;

}
