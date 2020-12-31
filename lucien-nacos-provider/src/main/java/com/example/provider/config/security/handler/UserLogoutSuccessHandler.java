package com.example.provider.config.security.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.consumer.feign.enums.ResultCode;
import com.example.consumer.feign.http.AjaxResult;
import com.example.provider.constants.CommonConstants;
import com.example.provider.utils.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @ClassName UserLogoutSuccessHandler
 * @User zhang
 * @Description 自定义实现系统登出，清除token
 * @Author Lucien
 * @Date 2020/12/10 14:41
 * @Version 1.0
 */
@Log4j2
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isNotEmpty(authHeader)) {
            String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
            log.info("real token = " + tokenValue);

            /*此处通过@Resource注解无法获得相关对象，故改为通过applicationContext上下文获得*/
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
            if (null != webApplicationContext) {
                /*获得TokenStore的Bean*/
                TokenStore tokenStore = webApplicationContext.getBean(TokenStore.class);
                /*获取redisUtils对象*/
                RedisUtil redisUtil = webApplicationContext.getBean(RedisUtil.class);
                /*redis所有和鉴权相关的key*/
                Set<String> authKeys = redisUtil.getKeysWithPrefix(CommonConstants.AUTH_PREFIX);

                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                /*token没过期的情况(通常access比refresh过期时间短)*/
                if (accessToken != null) {
                    // 清空 access token
                    tokenStore.removeAccessToken(accessToken);
                    // 清空 refresh token
                    OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
                    tokenStore.removeRefreshToken(refreshToken);
                } else {
                    if (!CollectionUtils.isEmpty(authKeys)) {
                        for (String key : authKeys) {
                            if (redisUtil.keyExists(key)) {
                                redisUtil.delKey(key);
                            }
                        }
                    }
                }
            }
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONUtil.toJsonStr(AjaxResult.success(ResultCode.SUCCESS.getCode(), "退出成功", null)));
    }
}
