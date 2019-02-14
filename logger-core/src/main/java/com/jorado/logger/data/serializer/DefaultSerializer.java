package com.jorado.logger.data.serializer;

import com.jorado.logger.util.JsonUtils;

import java.lang.reflect.Type;

public class DefaultSerializer implements Serializer {

    @Override
    public String serialize(Object model, int maxDepth) {
        return JsonUtils.toJson(model);
    }

    @Override
    public Object deserialize(String json, Type type) {
        return JsonUtils.fromJson(json, type.getClass());
    }
}
