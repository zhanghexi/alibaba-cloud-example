package com.example.provider.config.security.exception.auth;

import com.example.provider.config.security.exception.BootOauth2Exception;
import com.example.provider.config.security.exception.BootOauth2ExceptionJacksonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

/**
 * @ClassName ServerErrorException
 * @User zhang
 * @Description 自定义服务器内部错误
 * @Author Lucien
 * @Date 2020/9/23 11:21
 * @Version 1.0
 */
@JsonSerialize(using = BootOauth2ExceptionJacksonSerializer.class)
public class BootServerErrorException extends BootOauth2Exception {

    public BootServerErrorException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
