package com.smallyuan.labs.springmvc.controller;

import com.smallyuan.labs.springmvc.constants.ServiceExceptionEnum;
import com.smallyuan.labs.springmvc.exception.ServiceException;
import com.smallyuan.labs.springmvc.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
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


    /**
     * {@link HttpMessageConverter}
     *
     * 是否可以读取指定的mediaType内容类型，转换为对应的 clazz 对象
     *  boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);
     *
     * 是否可以将clazz对象，序列化为mediaType内容类型
     *  boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);
     *
     * 获取HttpMessageConverter能够支持的内容类型
     *  List<MediaType> getSupportedMediaTypes();
     *
     * 读取请求内容，转换成clazz对象
     *  T read(Class<? extends T> clazz, HttpInputMessage inputMessage)
     * 			throws IOException, HttpMessageNotReadableException;
     *
     * 将clazz对象，序列化为 contentType内容类型，写到响应。
     * 	void write(T t, @Nullable MediaType contentType, HttpOutputMessage outputMessage)
     * 			throws IOException, HttpMessageNotWritableException;
     *
     * 在请求时，我们在请求头 Content-Type 上，表示请求内容（RequestBody）的内容类型，SpringMvc会从自己的HttpMessageConverter数组中，
     * 通过canRead 方法，判断能否读取mediaType内容类型，转换为clazz对象。可以的化，调用read方法读取请求内容，转换为clazz对象
     *
     * 在响应时，我们在请求头 Accept 上，表示请求内容（ResponseBody）的内容类型，SpringMvc会从自己的HttpMessageConverter数组中，
     * 通过 canWrite 方法，判断是否能够将clazz对象，序列化为mediaType内容类型，可以的话，调用 write 方法，将clazz 序列化为 contentType
     * 内容类型，写入到响应
     * @param userVo
     * @return
     */
    /**
     * 同时支持 JSON/XML 格式提交数据，也同时支持 JSON/XML 格式响应数据。
     * @param userVo
     * @return
     */
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
