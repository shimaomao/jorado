package com.jorado.core.cache;

import com.jorado.core.util.TimeUtils;

import java.util.Date;

public class CacheItem<T> {

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    T value;
    Date expiry;

    public CacheItem(T value, Date expiry) {
        this.value = value;
        this.expiry = expiry;
    }

    @Override
    public String toString() {
        return String.format("value:%s;expiry:%s", value,
                TimeUtils.getStringDateShort(expiry));
    }
}
