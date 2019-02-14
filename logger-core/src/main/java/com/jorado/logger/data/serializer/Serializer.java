package com.jorado.logger.data.serializer;

import java.lang.reflect.Type;

/**
 * 序列化器接口
 * 理论上logger不应绑定任何json序列化包，使用端自行实现接口，框架自动扫描载入
 */
public interface Serializer {

    String serialize(Object model, int maxDepth);

    Object deserialize(String json, Type type);

}
