package com.smallyuan.labs.ratelimit.redis;

import com.google.common.collect.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;

@Aspect
@Configuration
public class LimitAspect {

    private static final Logger logger = LoggerFactory.getLogger(LimitAspect.class);

    private static final String SEPARATOR = "-";

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    private DefaultRedisScript<Number> redisScript;

    @Around("execution(*  com.smallyuan.labs.ratelimit.redis.LimitController ..*(..))")
    public Object test(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        if (rateLimit != null) {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

            // 存在redis里面的key要存什么。 ip+类名+方法名+key
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(getIpAddr(request)).append(SEPARATOR)
                    .append(method.getDeclaringClass()).append(SEPARATOR)
                    .append(method.getName()).append(SEPARATOR)
                    .append(rateLimit.key());
            System.out.println("keys:" + stringBuffer.toString());

            // keys对应脚本里面的KEYS[1],rateLimit.count()对应ARGV[1]限流大小)
            Number number = redisTemplate.execute(redisScript, Lists.newArrayList(stringBuffer.toString()),rateLimit.count(),rateLimit.time());

            if (number!= null && number.intValue()!= 0 && number.intValue() <= rateLimit.count()) {
                logger.info("有限流注解呢，还在正常访问.");
                return joinPoint.proceed();
            }
        } else {
            logger.info("没有限流注解.");
            return joinPoint.proceed();
        }
        throw new RuntimeException("已经被限流了呗~");
    }

    private static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }


}
