package com.smallyuan.labs.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class ValidationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ValidationApplication.class,args);
    }
}
