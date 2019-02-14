package com.jorado.logger.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by len.zhang on 2015/10/2.
 */
public final class JsonUtils {
    private JsonUtils() {
    }

    private static final ObjectMapper mapper = new ObjectMapper();
    //private static final PropertyNamingStrategy IGNORE_CASE = new IgnorePropertyNamingStrategy();

    static {
        mapper
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // .setPropertyNamingStrategy(IGNORE_CASE);
    }

    public static <T> T fromJson(String content, Class<T> cls) {
        if (StringUtils.isNullOrWhiteSpace(content)) return null;
        try {
            return mapper.readValue(content, cls);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(String content, TypeReference<T> trf) {
        if (StringUtils.isNullOrWhiteSpace(content)) return null;
        try {
            return mapper.readValue(content, trf);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String toJson(Object obj) {
        if (null == obj) return "[]";
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "[]";
        }
    }

//    /**
//     * 忽略命名规则，按照原始属性名输出
//     */
//    private static class IgnorePropertyNamingStrategy extends PropertyNamingStrategy {
//
//        public IgnorePropertyNamingStrategy() {
//
//        }
//
//        @Override
//        public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
//            return field.getName();
//        }
//
//        @Override
//        public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
//            return method.getName().substring(3);
//        }
//
//        @Override
//        public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
//            return method.getName().substring(3);
//        }
//    }
}
