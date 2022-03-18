package com.pengffe.pte.utils.annotation;

/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption 忽略接口注解
 * @date 24-05-2021
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SwaggerCustomIgnore {
}

