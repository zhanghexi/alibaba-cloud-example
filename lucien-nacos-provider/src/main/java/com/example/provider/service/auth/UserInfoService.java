package com.example.provider.service.auth;

import com.example.provider.data.model.auth.UserInfo;

/**
 * @ClassName UserInfoService
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/12/1 17:45
 * @Version 1.0
 */
public interface UserInfoService {

    /**
     * 返回用户DetailsService
     *
     * @param username
     * @return
     */
    UserInfo getUserInfo(String username);
}
