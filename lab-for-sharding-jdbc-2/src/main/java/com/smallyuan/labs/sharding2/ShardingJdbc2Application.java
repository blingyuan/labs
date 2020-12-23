package com.smallyuan.labs.sharding2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.smallyuan.labs.sharding2.mapper"})
public class ShardingJdbc2Application {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbc2Application.class,args);
    }
}
