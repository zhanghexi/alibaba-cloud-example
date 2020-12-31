package com.example.provider.config.security.handler;

import cn.hutool.json.JSONUtil;
import com.example.consumer.feign.enums.ResultCode;
import com.example.consumer.feign.http.AjaxResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthenticationAccessDeniedHandler
 * @User zhang
 * @Description 权限不足异常类重写，自定义403响应权限不足的内容
 * 用来解决认证过的用户访问无权限资源时的异常
 * @Author Lucien
 * @Date 2020/9/21 18:22
 * @Version 1.0
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSONUtil.toJsonStr(AjaxResult.error(ResultCode.FORBIDDEN.getCode(),
                ResultCode.FORBIDDEN.getMsg())));
    }
}