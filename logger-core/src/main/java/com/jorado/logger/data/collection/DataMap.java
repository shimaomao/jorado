package com.jorado.logger.data.collection;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DataMap extends ConcurrentHashMap<String, Object> {

    public DataMap() {
        super();
    }

    public DataMap(int initialCapacity) {
        super(initialCapacity);
    }

    public DataMap(List<Entry<String, Object>> values) {
        for (Entry<String, Object> entry : values)
            put(entry.getKey(), entry.getValue());
    }

}
