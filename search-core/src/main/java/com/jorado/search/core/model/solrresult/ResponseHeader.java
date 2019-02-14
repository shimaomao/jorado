package com.jorado.search.core.model.solrresult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2015/5/28.
 */
public class ResponseHeader {
    @JsonProperty("status")
    private int status;

    @JsonProperty("QTime")
    private int qTime;

    @JsonProperty("params")
    private Params params;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getqTime() {
        return qTime;
    }

    public void setqTime(int qTime) {
        this.qTime = qTime;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }
}

