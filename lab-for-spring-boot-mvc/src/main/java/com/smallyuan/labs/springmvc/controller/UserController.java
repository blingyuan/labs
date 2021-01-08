package com.smallyuan.labs.springmvc.controller;

import com.smallyuan.labs.springmvc.constants.ServiceExceptionEnum;
import com.smallyuan.labs.springmvc.exception.ServiceException;
import com.smallyuan.labs.springmvc.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

//            2021-01-08 16:57:02.653  INFO 13404 --- [nio-8080-exec-1] c.s.l.s.interceptor.FirstInterceptor     : [preHandle][handler(com.smallyuan.labs.springmvc.controller.UserController#doSomething())]
//            2021-01-08 16:57:02.653  INFO 13404 --- [nio-8080-exec-1] c.s.l.s.interceptor.ThirdInterceptor     : [preHandle][handler(com.smallyuan.labs.springmvc.controller.UserController#doSomething())]
//            2021-01-08 16:57:02.658  INFO 13404 --- [nio-8080-exec-1] c.s.l.s.controller.UserController        : [doSomething]
//            2021-01-08 16:57:02.705  INFO 13404 --- [nio-8080-exec-1] c.s.l.s.interceptor.ThirdInterceptor     : [postHandle][handler(com.smallyuan.labs.springmvc.controller.UserController#doSomething())]
//            2021-01-08 16:57:02.705  INFO 13404 --- [nio-8080-exec-1] c.s.l.s.interceptor.FirstInterceptor     : [postHandle][handler(com.smallyuan.labs.springmvc.controller.UserController#doSomething())]
//            2021-01-08 16:57:02.705  INFO 13404 --- [nio-8080-exec-1] c.s.l.s.interceptor.ThirdInterceptor     : [afterCompletion][handler(com.smallyuan.labs.springmvc.controller.UserController#doSomething())]
//            2021-01-08 16:57:02.709 ERROR 13404 --- [nio-8080-exec-1] o.s.web.servlet.HandlerExecutionChain    : HandlerInterceptor.afterCompletion threw exception
//            异常信息
//            2021-01-08 17:03:10.168  INFO 13404 --- [nio-8080-exec-5] c.s.l.s.interceptor.FirstInterceptor     : [afterCompletion][handler(com.smallyuan.labs.springmvc.controller.UserController#doSomething())]
    @GetMapping("/do_something")
    public void doSomething(){
        logger.info("[doSomething]");
    }


//            2021-01-08 17:04:03.350  INFO 13404 --- [nio-8080-exec-6] c.s.l.s.interceptor.FirstInterceptor     : [preHandle][handler(com.smallyuan.labs.springmvc.controller.UserController#currentUser())]
//            2021-01-08 17:04:03.350  INFO 13404 --- [nio-8080-exec-6] c.s.l.s.interceptor.SecondInterceptor    : [preHandle][handler(com.smallyuan.labs.springmvc.controller.UserController#currentUser())]
//            2021-01-08 17:04:03.350  INFO 13404 --- [nio-8080-exec-6] c.s.l.s.interceptor.FirstInterceptor     : [afterCompletion][handler(com.smallyuan.labs.springmvc.controller.UserController#currentUser())]
    @GetMapping("/current_user")
    public UserVo currentUser() {
        logger.info("[currentUser]");
        UserVo userVo = new UserVo();
        userVo.setId(10);
        userVo.setName(UUID.randomUUID().toString());
        return userVo;
    }


//    2021-01-08 17:05:44.306  INFO 13404 --- [nio-8080-exec-9] c.s.l.s.interceptor.FirstInterceptor     : [preHandle][handler(com.smallyuan.labs.springmvc.controller.UserController#exception03())]
//    2021-01-08 17:05:44.306  INFO 13404 --- [nio-8080-exec-9] c.s.l.s.interceptor.ThirdInterceptor     : [preHandle][handler(com.smallyuan.labs.springmvc.controller.UserController#exception03())]
//    2021-01-08 17:05:44.306  INFO 13404 --- [nio-8080-exec-9] c.s.l.s.controller.UserController        : [exception03]
//    2021-01-08 17:05:44.308 ERROR 13404 --- [nio-8080-exec-9] c.s.l.s.web.GlobalExceptionHandler       : [serviceExceptionHandler]
//    com.smallyuan.labs.springmvc.exception.ServiceException: 用户不存在
//    2021-01-08 17:05:44.309  INFO 13404 --- [nio-8080-exec-9] c.s.l.s.interceptor.ThirdInterceptor     : [afterCompletion][handler(com.smallyuan.labs.springmvc.controller.UserController#exception03())]
//    2021-01-08 17:05:44.310 ERROR 13404 --- [nio-8080-exec-9] o.s.web.servlet.HandlerExecutionChain    : HandlerInterceptor.afterCompletion threw exception
//    java.lang.RuntimeException: 故意抛个错误
//     2021-01-08 17:05:44.310  INFO 13404 --- [nio-8080-exec-9] c.s.l.s.interceptor.FirstInterceptor     : [afterCompletion][handler(com.smallyuan.labs.springmvc.controller.UserController#exception03())]
    @GetMapping("/exception-03")
    public void exception03() {
        logger.info("[exception03]");
        throw new ServiceException(ServiceExceptionEnum.USER_NOT_FOUND);
    }

    // http://www.iocoder.cn/Spring-Boot/SpringMVC/ todo
    @PostMapping(value = "/add",
            // ↓ 增加 "application/xml"、"application/json" ，针对 Content-Type 请求头
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            // ↓ 增加 "application/xml"、"application/json" ，针对 Accept 请求头
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserVo add(@RequestBody UserVo userVo) {
        return userVo;
    }
}
