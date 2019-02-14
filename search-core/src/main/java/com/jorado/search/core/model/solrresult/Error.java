package com.jorado.search.core.model.solrresult;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */
public class Error {

    @JsonProperty("metadata")
    private List<String> metadata;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("code")
    private int code;

    public List<String> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<String> metadata) {
        this.metadata = metadata;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}


