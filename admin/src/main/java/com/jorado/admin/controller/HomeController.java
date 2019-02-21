package com.jorado.admin.controller;

import com.jorado.core.Result;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */

@RestController
@EnableAutoConfiguration
public class HomeController {

    @RequestMapping("/")
    Result index() {
        return echo();
    }

    @RequestMapping("/echo")
    Result echo() {
        return Result.OK;
    }
}