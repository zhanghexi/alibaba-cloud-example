package com.example.provider.config.security.exception.auth;

import com.example.provider.config.security.exception.BootOauth2Exception;
import com.example.provider.config.security.exception.BootOauth2ExceptionJacksonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

/**
 * @ClassName MethodNotAllowedException
 * @User zhang
 * @Description 自定义方法不被允许访问(405)
 * @Author Lucien
 * @Date 2020/9/23 11:23
 * @Version 1.0
 */
@JsonSerialize(using = BootOauth2ExceptionJacksonSerializer.class)
public class BootMethodNotAllowedException extends BootOauth2Exception {

    public BootMethodNotAllowedException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase();
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.METHOD_NOT_ALLOWED.value();
    }
}
