# lab-for-spring-session

### [Spring Session](https://spring.io/projects/spring-session)
- Spring Session提供了用于管理用户会话信息的API和实现
- Features ： Spring Session使支持集群会话变得非常简单，无需绑定到特定于应用程序容器的解决方法。还提供了透明的集成
    - `HttpSession`: 允许以通用的方式替换应用程序容器（tomcat）中的HttpSession，并支持在请求头中提供sessionid
    - `WebSocket`: 提供在接收WebSocket消息时保持HttpSession活跃的能力
    - `WebSession`：允许以与应用程序容器无关的方式替换Spring WebFlux 的 WebSession
 - 可以使用Redis，JDBC（访问MySQL，Oracle等数据库），Hazelcast作为Session存储的数据源
 - Spring Session也另外提供了Spring Session MongoDB，实现使用MongoDB作为Session存储的数据源
 
 - session 外部化存储：基于应用层封装`HttpServletRequest`请求对象,包装成自己的`RequestWrapper`对象，从而让实现调用`HttpServletRequest#getSession()`方法时，获取读写外部存储器的`SessionWrapper`对象
 例如：Spring Session
```
@Order(SessionRepositoryFilter.DEFAULT_ORDER)
public class SessionRepositoryFilter<S extends Session> extends OncePerRequestFilter {
// ...
    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
        // sessionRepository 是访问外部数据源的操作类（比如 redis，mysql等）
		request.setAttribute(SESSION_REPOSITORY_ATTR, this.sessionRepository);
        // 将请求和响应包装成自己的 Wrapper
		SessionRepositoryRequestWrapper wrappedRequest = new SessionRepositoryRequestWrapper(request, response);
		SessionRepositoryResponseWrapper wrappedResponse = new SessionRepositoryResponseWrapper(wrappedRequest,
				response);
        // 继续执行时 通过封装的请求，响应进行处理。
		try {
			filterChain.doFilter(wrappedRequest, wrappedResponse);
		}
		finally {
			wrappedRequest.commitSession();
		}
	}
// ...
}
```
调用getSession时,返回的是自己封装的`HttpSessionWrapper`
```
org.springframework.session.web.http.SessionRepositoryFilter.SessionRepositoryRequestWrapper.getSession(boolean)
```
 后续，我们调用 HttpSessionWrapper 的方法，例如说 `HttpSessionWrapper#setAttribute(String name, Object value)` 方法，访问的就是外部数据源，而不是内存中。
 ```
// 这里的S session 就是 sessionRepository 
private final class HttpSessionWrapper extends HttpSessionAdapter<S> {
// ...
	HttpSessionWrapper(S session, ServletContext servletContext) {
		super(session, servletContext);
	}
// ...
}
```
 
 
 ### Spring Session + Redis
 - 引入依赖
```
        <!-- 实现对 Spring MVC 的自动化配置 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 实现对 Spring Session 使用 Redis 作为数据源的自动化配置 -->
        <dependency>
            <groupId>org.springframework.session</groupId>
            <artifactId>spring-session-data-redis</artifactId>
        </dependency>

        <!-- 实现对 Spring Data Redis 的自动化配置 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
            <exclusions>
                <!-- 去掉对 Lettuce 的依赖，因为 Spring Boot 优先使用 Lettuce 作为 Redis 客户端 -->
                <exclusion>
                    <groupId>io.lettuce</groupId>
                    <artifactId>lettuce-core</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!-- 引入 Jedis 的依赖，这样 Spring Boot 实现对 Jedis 的自动化配置 -->
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
        </dependency>
```
- application.yml
- SessionConfiguration
```
@Import({RedisHttpSessionConfiguration.class})
@Configuration(
    proxyBeanMethods = false
)
public @interface EnableRedisHttpSession {
    // Session 不活跃后的过期时间，默认为 1800 秒
    int maxInactiveIntervalInSeconds() default 1800;
    // 在 Redis 的 key 的统一前缀，默认为 "spring:session" 
    String redisNamespace() default "spring:session";
    // 会话刷新模式  
    // 1. FlushMode.ON_SAVE ，在请求执行完成时，统一写入 Redis 存储。 
    // 2. FlushMode.IMMEDIATE ，在每次修改 Session 时，立即写入 Redis 存储
    FlushMode flushMode() default FlushMode.ON_SAVE;
    // 清理 Redis Session 会话过期的任务执行 CRON 表达式,默认为 "0 * * * * *" 每分钟执行一次
    // Redis 自带了 key 的过期，但是惰性删除策略，实际过期的 Session 还在 Redis 中占用内存
    // Spring Session 通过定时任务，删除 Redis 中过期的 Session ，尽快释放 Redis 的内存
    String cleanupCron() default "0 * * * * *";

    SaveMode saveMode() default SaveMode.ON_SET_ATTRIBUTE;
}
```

