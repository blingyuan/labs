package com.smallyuan.labs.validation.config;

import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@Configuration
public class ValidationConfiguration {


    @Bean
    public Validator validator(MessageSource messageSource) {
        LocalValidatorFactoryBean validatorFactoryBean = ValidationAutoConfiguration.defaultValidator();
        // 设置 messageSource 属性，实现 il8 国际化
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }

}
