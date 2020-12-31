package com.example.provider.utils;

import com.example.provider.data.model.auth.LoginTokenUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @ClassName SecurityUtil
 * @User zhang
 * @Description 用户鉴权对象工具类
 * @Author Lucien
 * @Date 2020/12/11 16:22
 * @Version 1.0
 */
public class SecurityUtil {

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取username
     */
    public static String getUsername() {
        return getLoginUser().getUsername();
    }

    /**
     * 获取用户
     *
     * @param authentication
     * @return
     */
    public static LoginTokenUser getLoginUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof LoginTokenUser) {
            return (LoginTokenUser) principal;
        }
        return null;
    }

    /**
     * 获取用户
     */
    public static LoginTokenUser getLoginUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return getLoginUser(authentication);
    }
}
