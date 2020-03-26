package com.example.demo.permission.annotation;

import javax.persistence.Inheritance;
import java.lang.annotation.*;

/**
 * 自定义注解类
 *
 * @author alin
 * @
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inheritance
public @interface LoggerOperator {
    String description() default "";
}
