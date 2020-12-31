package com.example.provider.config.security.handler;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.example.consumer.feign.enums.ResultCode;
import com.example.consumer.feign.http.AjaxResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthExceptionEntryPoint
 * @User zhang
 * @Description 无效 token 异常类重写
 * 用来解决匿名用户无访问权限的异常(401)
 * @Author Lucien
 * @Date 2020/9/21 19:43
 * @Version 1.0
 */
@Log4j2
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        String requestUrl = request.getRequestURL().toString();
        log.info("请求路径:{}", requestUrl);

        response.setStatus(HttpStatus.HTTP_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSONUtil.toJsonStr(AjaxResult.error(ResultCode.UNAUTHORIZED.getCode(),
                ResultCode.UNAUTHORIZED.getMsg())));
    }
}