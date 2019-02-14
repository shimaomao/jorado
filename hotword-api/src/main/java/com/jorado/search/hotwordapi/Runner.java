package com.jorado.search.hotwordapi;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 系统启动时初始化操作
 */
@Component
@Order(1)
public class Runner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments var1) {

    }
}