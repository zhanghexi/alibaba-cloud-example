package com.example.provider.aop.annotation;

import java.lang.annotation.*;

/**
 * @ClassName OperationLog
 * @User zhang
 * @Description 记录aop日志的注解
 * @Author Lucien
 * @Date 2020/10/16 16:11
 * @Version 1.0
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface OperationLog {

    /* 操作事件(在注解上实际添加的描述)*/
    String operEvent() default "";

    /*1-查询，2-增删改，默认是1*/
    int operType() default 1;
}