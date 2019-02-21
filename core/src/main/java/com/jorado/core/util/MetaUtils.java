package com.jorado.core.util;

import java.lang.annotation.Annotation;

public final class MetaUtils {

    private MetaUtils() {
    }

    public static <T extends Annotation> T getAnnotation(Class<?> host, Class<T> meta) {
        if (host.isAnnotationPresent(meta)) {
            return host.getAnnotation(meta);
        } else
            return null;
    }

    public static <T extends Annotation> boolean hasAnnotation(Class<?> host, Class<T> meta) {
        return null != getAnnotation(host, meta);
    }
}
