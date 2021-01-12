package com.smallyuan.labs.validation.validator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = InEnumValidator.class)
public @interface InEunm {

    /**
     * @return 实现 IntArrayValuable 接口的
     */
    Class<? extends IntArrayValuable> value();

    /**
     * 提示内容
     * @return
     */
    String meaasge() default "必须在指定范围 {value}";
}
