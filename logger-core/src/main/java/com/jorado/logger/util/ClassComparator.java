package com.jorado.logger.util;

import java.util.Comparator;

public final class ClassComparator implements Comparator<Class<?>> {

    public static final ClassComparator COMPARATOR = new ClassComparator();

    private ClassComparator() {
    }

    public int compare(Class<?> o1, Class<?> o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        // 空值排在后面
        if (o1 == null) {
            return 1;
        }
        if (o2 == null) {
            return -1;
        }
        // 父类型排在后面
        if (o1.isAssignableFrom(o2)) { // o1是o2的父类型
            return 1;
        }
        if (o2.isAssignableFrom(o1)) { // o2是o1的父类型
            return -1;
        }
        // 互不相关的类型按类名排序
        return o1.getName().compareTo(o2.getName());
    }
}