package com.smallyuan.labs.ratelimit.redis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitController {

    @RateLimit(key = "testLimit")
    @RequestMapping("/testLimit")
    public String testLimit() {
        return "testLimit";
    }
}
