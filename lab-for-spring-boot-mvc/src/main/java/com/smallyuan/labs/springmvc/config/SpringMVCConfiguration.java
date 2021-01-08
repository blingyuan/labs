package com.smallyuan.labs.springmvc.config;

import com.smallyuan.labs.springmvc.interceptor.FirstInterceptor;
import com.smallyuan.labs.springmvc.interceptor.SecondInterceptor;
import com.smallyuan.labs.springmvc.interceptor.ThirdInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
}
