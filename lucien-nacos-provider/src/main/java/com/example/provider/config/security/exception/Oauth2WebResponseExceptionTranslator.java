package com.example.provider.config.security.exception;

import cn.hutool.core.util.StrUtil;
import com.example.consumer.feign.enums.ResultCode;
import com.example.provider.config.security.exception.auth.*;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.IOException;

/**
 * @ClassName Oauth2WebResponseExceptionTranslator
 * @User zhang
 * @Description 从异常栈中获取各种类型的异常并用我们自定义的BootOAuth2Exception进行处理
 * @Author Lucien
 * @Date 2020/12/2 13:57
 * @Version 1.0
 */
@Component("oauth2WebResponseExceptionTranslator")
public class Oauth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    @SneakyThrows
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        // 异常栈获取 OAuth2Exception 异常
        Exception ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
        /*401未授权异常(此处校验用户名输入错误的异常)*/
        if (ase != null) {
            String msg = e.getMessage();
            if (StrUtil.isBlank(msg)) {
                msg = e.getCause().getMessage();
            }
            return handleOAuth2Exception(new BootUnauthorizedException(msg, e));
        }
        /*权限不足异常(403)*/
        ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception(new BootForbiddenException(ase.getMessage(), ase));
        }
        /*登录异常(此处校验密码输入错误的异常，因为跟grant（InvalidGrantException）有关)*/
        ase = (InvalidGrantException) throwableAnalyzer.getFirstThrowableOfType(InvalidGrantException.class, causeChain);
        if (ase != null) {
            String msg = e.getMessage();
            if (msg.contains("Bad credentials")) {
                msg = ResultCode.USER_LOGIN_ERROR.getMsg();
                return handleOAuth2Exception(new BootInvalidException(msg, ase));
            }
            return handleOAuth2Exception(new BootInvalidException(msg, ase));
        }
        /*方法不被允许(405)*/
        ase = (HttpRequestMethodNotSupportedException) throwableAnalyzer
                .getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception(new BootMethodNotAllowedException(ase.getMessage(), ase));
        }

        ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception((OAuth2Exception) ase);
        }
        // 不包含上述异常则服务器内部错误
        return handleOAuth2Exception(new BootServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
    }

    /**
     * 使用自定义的异常处理类处理异常
     *
     * @param e
     * @return
     * @throws IOException
     */
    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {
        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CACHE_CONTROL, "no-store");
        headers.set(HttpHeaders.PRAGMA, "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
            headers.set(HttpHeaders.WWW_AUTHENTICATE, String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
        }
        /*客户端异常直接返回，否则无法解析*/
        if (e instanceof ClientAuthenticationException) {
            return new ResponseEntity<>(e, headers, HttpStatus.valueOf(status));
        }
        BootOauth2Exception exception = new BootOauth2Exception(e.getMessage(), e.getOAuth2ErrorCode());
        ResponseEntity<OAuth2Exception> response = new ResponseEntity<>(exception, headers, HttpStatus.OK);
        return response;
    }
}