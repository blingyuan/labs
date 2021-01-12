package com.smallyuan.labs.validation.validator;

import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class InEnumValidator implements ConstraintValidator<InEunm,Integer> {

    /**
     * 枚举的值数组
     */
    private Set<Integer> values;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // 校验通过
        if (values.contains(value)) {
            return true;
        }
        // 校验不通过，自定义提示语句
        context.disableDefaultConstraintViolation(); // 禁用默认的message的值
        context.buildConstraintViolationWithTemplate(
                context.getDefaultConstraintMessageTemplate().replaceAll("\\{value}",values.toString())
        ).addConstraintViolation(); // 重新添加错误提示语句
        return false;
    }

    @Override
    public void initialize(InEunm constraintAnnotation) {
        IntArrayValuable[] values = constraintAnnotation.value().getEnumConstants();
        if (values.length == 0) {
            this.values = Collections.emptySet();
        } else {
            this.values = Arrays.stream(values[0].array()).boxed().collect(Collectors.toSet());
        }
    }
}
