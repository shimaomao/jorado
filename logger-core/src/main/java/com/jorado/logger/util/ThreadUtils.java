package com.jorado.logger.util;

import com.jorado.logger.concurrent.NamedThreadFactory;

import java.util.concurrent.ThreadFactory;

public class ThreadUtils {
    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new NamedThreadFactory(name, daemon);
    }
}
