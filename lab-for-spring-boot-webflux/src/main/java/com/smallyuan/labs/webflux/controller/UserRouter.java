package com.smallyuan.labs.webflux.controller;

import com.smallyuan.labs.webflux.vo.UserVo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * 基于函数式编程方式
 */
@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> userListRouterFunction() {
        return route(GET("/users2/list"), serverRequest -> {
            List<UserVo> result = new ArrayList<>();
            UserVo vo = new UserVo();
            vo.setId(1);
            vo.setName("yuan");
            result.add(vo);
            return ok().bodyValue(result);
        });
    }

    @Bean
    public RouterFunction<ServerResponse> usersGetRouterFunction() {
        return route(GET("/users2/get"), serverRequest -> {
            // 获得编号
            Integer id = serverRequest.queryParam("id")
                    .map(s -> StringUtils.hasText(s) ? Integer.valueOf(s) : null).get();
            // 查询用户
            UserVo user = new UserVo();
            user.setId(id);
            user.setName(UUID.randomUUID().toString());
            // 返回列表
            return ok().bodyValue(user);
        });
    }

    /**
     * 简洁写法，需要静态引入
     * import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
     * import static org.springframework.web.reactive.function.server.RouterFunctions.route;
     * import static org.springframework.web.reactive.function.server.ServerResponse.ok;
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> demoRouterFunction() {
        return route(GET("/users/demo"),serverRequest -> ok().bodyValue("demo"));
    }

}
