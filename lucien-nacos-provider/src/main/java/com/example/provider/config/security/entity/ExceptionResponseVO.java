package com.example.provider.config.security.entity;

import lombok.Getter;

import java.io.Serializable;

/**
 * @ClassName ExceptionResponseEntity
 * @User zhang
 * @Description 统一返回的异常信息的格式
 * @Author Lucien
 * @Date 2020/12/2 13:41
 * @Version 1.0
 */
@Getter
public class ExceptionResponseVO implements Serializable {

    private int code;
    private String msg;

    public ExceptionResponseVO() {
    }

    public ExceptionResponseVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}