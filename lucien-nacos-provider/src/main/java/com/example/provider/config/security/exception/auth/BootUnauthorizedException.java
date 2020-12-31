package com.example.provider.config.security.exception.auth;

import com.example.consumer.feign.enums.ResultCode;
import com.example.provider.config.security.exception.BootOauth2Exception;
import com.example.provider.config.security.exception.BootOauth2ExceptionJacksonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

/**
 * @ClassName UnauthorizedException
 * @User zhang
 * @Description 自定义未授权异常(401)
 * @Author Lucien
 * @Date 2020/9/23 11:22
 * @Version 1.0
 */
@JsonSerialize(using = BootOauth2ExceptionJacksonSerializer.class)
public class BootUnauthorizedException extends BootOauth2Exception {

    public BootUnauthorizedException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return String.valueOf(ResultCode.USER_LOGIN_ERROR.getCode());
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
