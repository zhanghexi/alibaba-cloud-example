package com.example.consumer.feign.exception;

import lombok.Getter;

/**
 * @ClassName CommonException
 * @User zhang
 * @Description 统一的异常类封装
 * 另外Spring 对于 RuntimeException类型的 异常才会进行事务回滚
 * @Author Lucien
 * @Date 2020/9/25 11:39
 * @Version 1.0
 */
@Getter
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;

    public CommonException(String msg) {
        this.msg = msg;
    }

    public CommonException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
