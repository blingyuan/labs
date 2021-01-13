package com.smallyuan.labs.dubbo02;

import com.smallyuan.labs.dubbo02.api.UserRpcService;
import com.smallyuan.labs.dubbo02.dto.UserDTO;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }

    @Component
    public class UserRpcServiceTest implements CommandLineRunner {
        private final Logger logger = LoggerFactory.getLogger(UserRpcServiceTest.class);

        @Reference(version = "${dubbo.consumer.UserRpcService.version}")
        private UserRpcService userRpcService;

        @Override
        public void run(String... args) throws Exception {
            UserDTO userDTO = userRpcService.get(1);
            logger.info("[run][发起一次 Dubbo RPC 请求，获得用户为({})", userDTO);
        }
    }
}
