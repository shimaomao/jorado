package com.jorado.search.core.model.solrresult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2015/5/28.
 */
public class Result<T> {

    @JsonProperty("responseHeader")
    private ResponseHeader responseHeader;

    @JsonProperty("response")
    private Response<T> response;

    @JsonProperty("error")
    private Error error;

    @JsonProperty("debug")
    private Debug debug;

    @JsonProperty("facet_counts")
    private FacetCounts facetCounts;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public Response<T> getResponse() {
        return response;
    }

    public void setResponse(Response<T> response) {
        this.response = response;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }

    public FacetCounts getFacetCounts() {
        return facetCounts;
    }

    public void setFacetCounts(FacetCounts facetCounts) {
        this.facetCounts = facetCounts;
    }
}
