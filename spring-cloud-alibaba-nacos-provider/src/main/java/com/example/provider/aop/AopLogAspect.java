package com.example.provider.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @ClassName AopLogAspect
 * @User zhang
 * @Description 切面配置类
 * @Author Lucien
 * @Date 2020/8/21 14:52
 * @Version 1.0
 */
@Component
@Aspect
@Log4j2
public class AopLogAspect {

    private final String POINT_CUT = "execution(* com.example.provider.service.impl..*.*(..))";
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 配置总的切面路径规则
     */
    @Pointcut(POINT_CUT)
    public void pointCut() {
    }

    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        /*获取request对象*/
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //打印请求内容
        log.info("==================[Before]请求内容start==================");
        log.info("==================[Before]请求地址==================\n{}", request.getRequestURL().toString());
        log.info("==================[Before]请求方式==================\n{}", request.getMethod());
        log.info("==================[Before]请求类方法==================\n{}", joinPoint.getSignature());
        log.info("==================[Before]请求类方法参数==================\n{}", Arrays.toString(joinPoint.getArgs()));
        log.info("==================[Before]请求内容end==================");
    }

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("==================[Around]方法环绕前置start==================");
        //这句必须有 往下执行方法
        Object result = joinPoint.proceed();
        log.info("==================[Around]方法环绕后置start==================");
        log.info("==================[Around]方法环绕end==================");
        return result;
    }

    @After(value = "pointCut()")
    public void after() {
        log.info("==================[After]切点方法执行后==================");
    }

    @AfterThrowing(value = "pointCut()", throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        log.error("==================[AfterThrowingMethod]==================\n切点方法{}抛出异常:{}", methodName, ex.getMessage());
    }


    @AfterReturning(value = "pointCut()", returning = "o")
    public void afterReturn(Object o) {
        log.info("==================[AfterReturn]==================\nResponse内容:{}", JSON.toJSONString(o));
        log.info("==================[AfterReturn]==================\n请求消耗总时间:{}ms", (System.currentTimeMillis() - startTime.get()));
    }
}
