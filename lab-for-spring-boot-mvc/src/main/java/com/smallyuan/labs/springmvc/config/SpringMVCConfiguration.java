package com.smallyuan.labs.springmvc.config;

import com.smallyuan.labs.springmvc.interceptor.FirstInterceptor;
import com.smallyuan.labs.springmvc.interceptor.SecondInterceptor;
import com.smallyuan.labs.springmvc.interceptor.ThirdInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@Configuration
public class SpringMVCConfiguration implements WebMvcConfigurer {

    @Bean
    public FirstInterceptor firstInterceptor() {
        return new FirstInterceptor();
    }

    @Bean
    public SecondInterceptor secondInterceptor() {
        return new SecondInterceptor();
    }

    @Bean
    public ThirdInterceptor thirdInterceptor() {
        return new ThirdInterceptor();
    }

    /**
     * 正序执行所有拦截器的 preHandle 方法 -> 这里如果某个拦截器的 preHandle 返回 false，后续链路不用再执行了，执行这个拦截器前的其他拦截器的 afterCompletion 方法
     * 执行调用api的handler -> 如果这里出现异常，倒叙执行所有拦截器的 afterCompletion 方法 （某个afterCompletion方法出现异常，也不影响后续其他拦截器的afterCompletion方法执行）
     * 倒叙执行所有拦截器的 postHandle
     * 倒叙执行所有拦截器的 afterCompletion
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.firstInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(this.secondInterceptor()).addPathPatterns("/users/current_user");
        registry.addInterceptor(this.thirdInterceptor()).addPathPatterns("/**");
    }

    /**
     * 用 CorsFilter 解决跨域问题
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        // 创建 UrlBasedCorsConfigurationSource 配置源，类似 CorsRegistry 注册表
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 创建 CorsConfiguration 配置，相当于 CorsRegistration 注册信息
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("*")); // 允许所有请求来源
        config.setAllowCredentials(true); // 允许发送 Cookie
        config.addAllowedMethod("*"); // 允许所有请求 Method
        config.setAllowedHeaders(Collections.singletonList("*")); // 允许所有请求 Header
        // config.setExposedHeaders(Collections.singletonList("*")); // 允许所有响应 Header
        config.setMaxAge(1800L); // 有效期 1800 秒，2 小时
        source.registerCorsConfiguration("/**", config);
        // 创建 CorsFilter 过滤器
        CorsFilter filter = new CorsFilter(source);
        // 创建 FilterRegistrationBean 对象
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(0); // 设置 order 排序。这个顺序很重要哦，为避免麻烦请设置在最前
        return bean;
    }
}
