package com.smallyuan.labs.springmvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/test")
    public void test() throws InterruptedException {
        logger.error("start..................");
        Thread.sleep(20000);
        logger.error("finish.............................");
    }
}
