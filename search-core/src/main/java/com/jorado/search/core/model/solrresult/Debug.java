package com.jorado.search.core.model.solrresult;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */
public class Debug {

    @JsonProperty("rawquerystring")
    private String rawquerystring;

    @JsonProperty("querystring")
    private String querystring;

    @JsonProperty("parsedquery")
    private String parsedquery;

    @JsonProperty("parsedquery_toString")
    private String parsedqueryToString;

    @JsonProperty("explain")
    public Map<String, String> explain;

    @JsonProperty("QParser")
    private String qParser;

    @JsonProperty("altquerystring")
    public String altquerystring;

    @JsonProperty("boost_queries")
    public Object boostQueries;

    @JsonProperty("parsed_boost_queries")
    public List<Object> parsedBoostQueries;

    @JsonProperty("boostfuncs")
    public Object boostfuncs;

    @JsonProperty("timing")
    public Timing timing;

    public String getRawquerystring() {
        return rawquerystring;
    }

    public void setRawquerystring(String rawquerystring) {
        this.rawquerystring = rawquerystring;
    }

    public String getQuerystring() {
        return querystring;
    }

    public void setQuerystring(String querystring) {
        this.querystring = querystring;
    }

    public String getParsedquery() {
        return parsedquery;
    }

    public void setParsedquery(String parsedquery) {
        this.parsedquery = parsedquery;
    }

    public String getParsedqueryToString() {
        return parsedqueryToString;
    }

    public void setParsedqueryToString(String parsedqueryToString) {
        this.parsedqueryToString = parsedqueryToString;
    }

    public Map<String, String> getExplain() {
        return explain;
    }

    public void setExplain(Map<String, String> explain) {
        this.explain = explain;
    }

    public String getqParser() {
        return qParser;
    }

    public void setqParser(String qParser) {
        this.qParser = qParser;
    }

    public String getAltquerystring() {
        return altquerystring;
    }

    public void setAltquerystring(String altquerystring) {
        this.altquerystring = altquerystring;
    }

    public Object getBoostQueries() {
        return boostQueries;
    }

    public void setBoostQueries(Object boostQueries) {
        this.boostQueries = boostQueries;
    }

    public List<Object> getParsedBoostQueries() {
        return parsedBoostQueries;
    }

    public void setParsedBoostQueries(List<Object> parsedBoostQueries) {
        this.parsedBoostQueries = parsedBoostQueries;
    }

    public Object getBoostfuncs() {
        return boostfuncs;
    }

    public void setBoostfuncs(Object boostfuncs) {
        this.boostfuncs = boostfuncs;
    }

    public Timing getTiming() {
        return timing;
    }

    public void setTiming(Timing timing) {
        this.timing = timing;
    }
}
