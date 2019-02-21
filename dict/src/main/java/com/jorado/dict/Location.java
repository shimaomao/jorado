package com.jorado.dict;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jorado.dict.location.All;
import com.jorado.dict.location.Hotcity;
import com.jorado.dict.location.Other;
import com.jorado.dict.location.Province;

import java.util.List;

/*
地理位置
 */
public class Location {

    @JsonProperty("province")
    private List<Province> province;

    @JsonProperty("all")
    private List<All> all;

    @JsonProperty("other")
    private List<Other> other;

    @JsonProperty("hotcitys")
    private List<Hotcity> hotcitys;

    public List<Province> getProvince() {
        return province;
    }

    public void setProvince(List<Province> province) {
        this.province = province;
    }

    public List<All> getAll() {
        return all;
    }

    public void setAll(List<All> all) {
        this.all = all;
    }

    public List<Other> getOther() {
        return other;
    }

    public void setOther(List<Other> other) {
        this.other = other;
    }

    public List<Hotcity> getHotcitys() {
        return hotcitys;
    }

    public void setHotcitys(List<Hotcity> hotcitys) {
        this.hotcitys = hotcitys;
    }
}
