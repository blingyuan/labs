# labs-for-spring-boot-webflux

响应式编程：关于非阻塞应用程序的，异步，事件驱动的。

Java生态中的Reactor框架主要有：[Reactor](https://projectreactor.io/)，[RxJava](https://github.com/ReactiveX/RxJava)，[JDK9 Flow API](https://community.oracle.com/tech/developers/discussion/4418040/reactive-programming-with-jdk-9-flow-api)

Reactor中两个非常重要的基础概念：
- Flux：表示包含0到N个元素的异步序列。当消息通知产生时，订阅者（Subscriber）中对应的方法 `#onNext()`,`#onComplete`,`#onError`会被调用
- Mono：表示包含0或者1个元素的异步序列。该序列中同样可以包含与flux相同的三种类型的消息通知。
- Flux 和 Mono 之间可以进行转换，例如：
    - 对一个Flux序列进行计数操作，得到的结果是一个`Mono<Long>`对象
    - 把两个Mono序列合并在一起，得到的是一个Flux对象
    
 ### Spring WebFlux [官方文档](https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html)
 
 Spring Framework 5 提供了一个新的 spring-webflux 模块。该模块包含了：
 
 - 对响应式支持的 HTTP 和 WebSocket 客户端。
 - 对响应式支持的 Web 服务器，包括 Rest API、HTML 浏览器、WebSocket 等交互方式。
 
 在服务端方面，WebFlux 提供了 2 种编程模型（翻译成使用方式，可能更易懂）：
 
 - 方式一，基于 Annotated Controller 方式实现：基于 @Controller 和 SpringMVC 使用的其它注解。我们大体上可以像使用 SpringMVC 的方式，使用 WebFlux 。
 - 方式二，基于函数式编程方式：函数式，Java 8 lambda 表达式风格的路由和处理。
 
 WebFlux 可以运行在：
 
 - 支持 Servlet 3.1 非阻塞 IO API 的 Servlet 容器上
 - 也可以运行在支持异步运行时的，例如说 Netty 或者 Undertow 上
 
 - 对于 Servlet 来说， ServletRequest#getInputStream() 方法，获得请求的主体内容返回的是 InputStream 对象。
 - 对于 WebFlux 来说，ServerHttpRequest#getBody() 方法，获得请求的主体内容返回的是 Flux<DataBuffer> 对象。
 
 REST 风格 API 使用到的 JSON 和 XML 序列化和反序列化，需要提供对 Flux<Object> 的支持。对于 HTML 渲染，和 SSE 也要提供对 Flux<Object> 的支持。
 
 ###快速入门
 - 引入依赖
 ```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```
- 样例
```
com.smallyuan.labs.webflux.controller.UserController
com.smallyuan.labs.webflux.controller.UserRouter

```
- 集成测试
```
com.smallyuan.labs.webflux.UserControllerTest
```
- 在类上，我们添加了 @AutoConfigureWebTestClient 注解，用于自动化配置我们稍后注入的 WebTestClient Bean 对象
- 每次 API 接口的请求，都通过 `RequestHeadersSpec` 来构建。构建完成后，通过 `RequestHeadersSpec#exchange()` 方法来执行请求，返回 `ResponseSpec` 结果
    - WebTestClient 的 #get()、#head()、#delete()、#options() 方法，返回的是 RequestHeadersUriSpec 对象。
    - WebTestClient 的 #post()、#put()、#delete()、#patch() 方法，返回的是 RequestBodyUriSpec 对象。
    - RequestHeadersUriSpec 和 RequestBodyUriSpec 都继承了 RequestHeadersSpec 接口。 
- 执行完请求后，通过调用 RequestBodyUriSpec 的各种断言方法，添加对结果的预期，相当于做断言。如果不符合预期，则会抛出异常，测试不通过。

- 全局统一返回
```
com.smallyuan.labs.webflux.vo.CommonResult
com.smallyuan.labs.webflux.web.GlobalResponseBodyHandler
com.smallyuan.labs.webflux.config.WebFluxConfiguration
```

- 全局异常处理
```

```