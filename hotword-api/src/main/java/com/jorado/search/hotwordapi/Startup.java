package com.jorado.search.hotwordapi;

import com.jorado.logger.EventClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.jorado.search.hotword", "com.jorado.search.hotwordapi"})
public class Startup {

    public static void main(String[] args) {

        EventClient.getDefault().submitLog("Startup begin.....");

        SpringApplication.run(Startup.class, args);
    }
}
