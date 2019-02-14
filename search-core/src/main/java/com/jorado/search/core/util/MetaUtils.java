package com.jorado.search.core.util;

import org.springframework.context.annotation.Description;

import java.lang.annotation.Annotation;

public final class MetaUtils {

    private MetaUtils() {
    }

    public static <T extends Annotation> T getAnnotation(Class<?> host, Class<T> meta) {
        if (host.isAnnotationPresent(meta)) {
            return host.getAnnotation(meta);
        } else {
            return null;
        }
    }

    /**
     * 获取描述信息
     *
     * @return
     */
    public static String getDescription(Class<?> host) {
        Description description = getAnnotation(host, Description.class);
        if (null == description) {
            return host.getName();
        }
        return description.value();
    }
}
