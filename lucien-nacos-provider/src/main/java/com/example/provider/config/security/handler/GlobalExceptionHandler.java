package com.example.provider.config.security.handler;

import com.example.consumer.feign.enums.ResultCode;
import com.example.consumer.feign.exception.CommonException;
import com.example.consumer.feign.http.AjaxResult;
import com.example.provider.config.security.entity.ExceptionResponseVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @ClassName GlobalExceptionHandler
 * @User zhang
 * @Description 全局异常处理类使用 @RestControllerAdvice + @ExceptionHandler 注解方式实现全局异常处理
 * @ControllerAdvice 捕获 Controller 层抛出的异常，如果添加 @ResponseBody 返回信息则为JSON 格式。
 * @RestControllerAdvice 相当于 @ControllerAdvice 与 @ResponseBody 的结合体。
 * @ExceptionHandler 统一处理一种类的异常，减少代码重复率，降低复杂度。
 * 因为我们这里全部异常信息都约定返回json，所以直接使用 @RestControllerAdvice 代替 @ControllerAdvice
 * 这样在方法上就可以不需要添加 @ResponseBody了。步骤：
 * 1.创建一个 GlobalExceptionHandler 类，并添加上 @RestControllerAdvice 注解就可以实现异常通知类的定义了
 * 2.定义的方法中添加上 @ExceptionHandler 即可实现Controller层的异常捕捉
 * @Author Lucien
 * @Date 2020/9/25 15:01
 * @Version 1.0
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ExceptionResponseVO unknownExceptionHandler(Exception e) {
        log.error("全局处理未知异常:{}", e.getMessage(), e);
        return new ExceptionResponseVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    /**
     * 登录异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public AjaxResult handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error("用户登录异常:{}", e.getMessage(), e);
        return AjaxResult.error(ResultCode.USER_LOGIN_ERROR.getCode(), ResultCode.USER_LOGIN_ERROR.getMsg());
    }

    /**
     * 处理所有业务异常
     * ExceptionHandler注解 申明捕获哪个异常类
     *
     * @param e
     * @return
     */
    @ExceptionHandler({CommonException.class})
    public AjaxResult commonExceptionHandler(CommonException e) {
        log.info("业务异常:{}", e.getMsg());
        return AjaxResult.error(e.getCode(), e.getMsg());
    }
}
