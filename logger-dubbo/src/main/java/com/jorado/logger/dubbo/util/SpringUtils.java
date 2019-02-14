package com.jorado.logger.dubbo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public final class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = arg0;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBeanById(String id) {
        return getApplicationContext().getBean(id);
    }

    public static Object getBeanByClass(Class clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static Map getBeansByClass(Class clazz) {
        return getApplicationContext().getBeansOfType(clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String id, Class<T> clazz) {
        return getApplicationContext().getBean(id, clazz);
    }
}