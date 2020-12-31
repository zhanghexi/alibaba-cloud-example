package com.example.provider.config.security.exception.auth;

import com.example.consumer.feign.enums.ResultCode;
import com.example.provider.config.security.exception.BootOauth2Exception;
import com.example.provider.config.security.exception.BootOauth2ExceptionJacksonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

/**
 * @ClassName BootInvalidExeption
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/9/25 10:28
 * @Version 1.0
 */
@JsonSerialize(using = BootOauth2ExceptionJacksonSerializer.class)
public class BootInvalidException extends BootOauth2Exception {

    public BootInvalidException(String msg, Throwable t) {
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
