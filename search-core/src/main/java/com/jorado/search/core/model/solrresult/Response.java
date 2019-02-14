package com.jorado.search.core.model.solrresult;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Administrator on 2015/5/28.
 */
public class Response<T> {
    @JsonProperty("numFound")
    private int numFound;

    @JsonProperty("start")
    private int start;

    @JsonProperty("docs")
    private List<T> docs;

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<T> getDocs() {
        return docs;
    }

    public void setDocs(List<T> docs) {
        this.docs = docs;
    }
}
