package com.example.provider.data.model.auth;

import com.example.provider.data.model.AuthRole;
import com.example.provider.data.model.AuthUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @ClassName UserInfo
 * @User zhang
 * @Description 封装用户信息返回(基础信息 、 角色)
 * @Author Lucien
 * @Date 2020/12/1 17:44
 * @Version 1.0
 */
@Data
public class UserInfo implements Serializable {

    private AuthUser authUser;
    private Set<String> authRoleSet;
}