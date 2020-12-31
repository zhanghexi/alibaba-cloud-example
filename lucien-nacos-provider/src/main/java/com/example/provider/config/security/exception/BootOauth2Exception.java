package com.example.provider.config.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @ClassName BootOAuth2Exception
 * @User zhang
 * @Description 自定义认证鉴权异常类
 * @Author Lucien
 * @Date 2020/9/22 22:29
 * @Version 1.0
 */
@JsonSerialize(using = BootOauth2ExceptionJacksonSerializer.class)
public class BootOauth2Exception extends OAuth2Exception {

    @Getter
    private String errorCode;

    public BootOauth2Exception(String msg) {
        super(msg);
    }

    public BootOauth2Exception(String msg, String errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}