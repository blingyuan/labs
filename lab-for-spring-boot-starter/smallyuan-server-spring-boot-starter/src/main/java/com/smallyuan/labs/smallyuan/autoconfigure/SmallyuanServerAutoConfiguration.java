package com.smallyuan.labs.smallyuan.autoconfigure;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * 实现一个java内置的HttpServer服务器的自动化配置
 */
@Configuration
@EnableConfigurationProperties(SmallyuanServerProperties.class)
public class SmallyuanServerAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(SmallyuanServerAutoConfiguration.class);

    @Bean
    @ConditionalOnClass(HttpServer.class)  // 需要项目中存在 com.sun.net.httpserver.HttpServer 类。该类为 JDK 自带，所以一定成立。
    public HttpServer httpServer(SmallyuanServerProperties serverProperties) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(serverProperties.getPort()), 0);
        server.start();
        logger.info("[httpServer][启动服务器成功，端口为:{}]", serverProperties.getPort());
        return server;
    }

}
