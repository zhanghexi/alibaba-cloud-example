package com.example.consumer.feign.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ImgCodeDTO
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/11/30 18:00
 * @Version 1.0
 */
@Data
public class LoginParamDTO implements Serializable {

    /**
     * 登录账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 校验码
     */
    private String validateCode;
    /**
     * 是否"记住我"
     */
    private Boolean rememberMe;
    /**
     * uuid
     */
    private String uuid;
}