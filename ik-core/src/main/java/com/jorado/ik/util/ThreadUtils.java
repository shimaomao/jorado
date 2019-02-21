package com.jorado.ik.util;

import com.jorado.ik.concurrent.NamedThreadFactory;

import java.util.concurrent.ThreadFactory;

public class ThreadUtils {
    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new NamedThreadFactory(name, daemon);
    }
}
