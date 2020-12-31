package com.example.provider.config.security.annotation;

import java.lang.annotation.*;

/**
 * @ClassName AuthIgnore
 * @User zhang
 * @Description 忽略token、直接开放api的注解
 * @Author Lucien
 * @Date 2020/12/11 11:33
 * @Version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthIgnore {
}