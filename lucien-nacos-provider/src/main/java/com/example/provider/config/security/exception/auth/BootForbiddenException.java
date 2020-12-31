package com.example.provider.config.security.exception.auth;

import com.example.provider.config.security.exception.BootOauth2Exception;
import com.example.provider.config.security.exception.BootOauth2ExceptionJacksonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

/**
 * @ClassName ForbiddenException
 * @User zhang
 * @Description 自定义权限不足异常(403)
 * @Author Lucien
 * @Date 2020/9/23 10:56
 * @Version 1.0
 */
@JsonSerialize(using = BootOauth2ExceptionJacksonSerializer.class)
public class BootForbiddenException extends BootOauth2Exception {

    public BootForbiddenException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return HttpStatus.FORBIDDEN.getReasonPhrase();
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.FORBIDDEN.value();
    }
}
