package com.example.provider.config.security.enhancer;

import cn.hutool.http.HttpStatus;
import com.example.consumer.feign.enums.ResultCode;
import com.example.provider.data.model.auth.LoginTokenUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CustomTokenEnhancer
 * @User zhang
 * @Description 自定义token生成令牌的格式
 * @Author Lucien
 * @Date 2020/12/2 0:34
 * @Version 1.0
 */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (authentication.getUserAuthentication() != null) {
            Map<String, Object> tokenMap = new HashMap<>(16);
            LoginTokenUser loginTokenUser = (LoginTokenUser) authentication.getUserAuthentication().getPrincipal();
            tokenMap.put("username", loginTokenUser.getUsername());
            tokenMap.put("user_id", loginTokenUser.getId());
            tokenMap.put("authorities", loginTokenUser.getAuthorities().toArray());
            tokenMap.put("code", HttpStatus.HTTP_OK);
            tokenMap.put("msg", ResultCode.SUCCESS.getMsg());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(tokenMap);
        }
        return accessToken;
    }
}