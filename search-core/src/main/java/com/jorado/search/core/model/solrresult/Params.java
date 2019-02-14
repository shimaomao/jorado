package com.jorado.search.core.model.solrresult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2015/5/28.
 */
public class Params {
    @JsonProperty("fl")
    private String fl;

    @JsonProperty("indent")
    private String indent;

    @JsonProperty("q.op")
    private String op;

    @JsonProperty("q")
    private String q;

    @JsonProperty("wt")
    private String wt;

    @JsonProperty("rows")
    private String rows;

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getIndent() {
        return indent;
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getWt() {
        return wt;
    }

    public void setWt(String wt) {
        this.wt = wt;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }
}
