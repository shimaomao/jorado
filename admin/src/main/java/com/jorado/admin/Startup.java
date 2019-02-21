package com.jorado.admin;

import com.jorado.logger.EventClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */

@SpringBootApplication
public class Startup {

    public static void main(String[] args) {

        EventClient.getDefault().submitLog("Startup begin.....");

        SpringApplication.run(Startup.class, args);
    }
}
