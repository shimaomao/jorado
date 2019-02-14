package com.jorado.logger.data;

import com.jorado.logger.data.collection.DataMap;
import com.jorado.logger.util.StringUtils;

import java.io.Serializable;

public class BaseData implements Serializable {

    private DataMap data;

    public BaseData() {
        this.data = new DataMap(16);
    }

    public DataMap getData() {
        return data;
    }

    public Object getValue(String key) {
        if (!containsData(key))
            return null;

        return this.data.get(key);
    }

    public <T> T getValue(String key, Class<T> cls) {
        if (!containsData(key))
            return null;

        try {
            return cls.cast(this.data.get(key));
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean containsData(String key) {
        if (StringUtils.isNullOrWhiteSpace(key))
            return false;
        return this.data.containsKey(key);
    }

    public void addData(String key, Object value) {
        if (null == value)
            value = "null";
        this.data.remove(key);
        this.data.put(key, value);
    }

    public void clearData() {
        this.data.clear();
    }
}
