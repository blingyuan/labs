package com.smallyuan.labs.dubbo;

import com.smallyuan.labs.dubbo.api.UserRpcService;
import com.smallyuan.labs.dubbo.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@SpringBootApplication
@ImportResource("classpath:dubbo.xml")
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }

    @Component
    public class UserRpcServiceTest implements CommandLineRunner {
        private final Logger logger = LoggerFactory.getLogger(UserRpcServiceTest.class);

        @Resource
        private UserRpcService userRpcService;

        @Override
        public void run(String... args) throws Exception {
            UserDTO userDTO = userRpcService.get(1);
            logger.info("[run][发起一次 Dubbo RPC 请求，获得用户为({})", userDTO);
        }
    }
}
