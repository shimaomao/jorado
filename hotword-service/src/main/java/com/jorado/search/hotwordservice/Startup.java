package com.jorado.search.hotwordservice;

import com.jorado.logger.EventClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

/**
 * @author len.zhang
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.jorado.search.hotword", "com.jorado.search.hotwordservice"})
public class Startup {


    public static void main(String[] args) throws IOException {

        EventClient.getDefault().submitLog("Startup begin.....");

        SpringApplication.run(Startup.class, args);

        System.in.read();
    }

}
