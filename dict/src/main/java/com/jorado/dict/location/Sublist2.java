package com.jorado.dict.location;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Created by len.zhang on 2018/4/2.
 *
 */
public class Sublist2 {

    @JsonProperty("en_name")
    private String enName;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

