package com.example.provider.service;

import com.example.consumer.feign.http.AjaxResult;
import com.example.consumer.feign.model.LoginParamDTO;
import com.example.provider.data.model.AuthUser;

/**
 * @ClassName UserService
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/11/30 22:08
 * @Version 1.0
 */
public interface UserService {

    AjaxResult doLogin(LoginParamDTO loginParamDTO);

    AuthUser selectUserById(Integer id);
}
