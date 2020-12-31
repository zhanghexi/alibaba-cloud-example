package com.example.provider.service.auth.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.consumer.feign.enums.ResultCode;
import com.example.consumer.feign.exception.CommonException;
import com.example.provider.data.mapper.AuthRoleMapper;
import com.example.provider.data.mapper.AuthUserMapper;
import com.example.provider.data.model.AuthRole;
import com.example.provider.data.model.AuthUser;
import com.example.provider.data.model.auth.UserInfo;
import com.example.provider.service.auth.UserInfoService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName UserInfoServiceImpl
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/12/1 17:46
 * @Version 1.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private AuthUserMapper authUserMapper;
    @Resource
    private AuthRoleMapper authRoleMapper;

    @Override
    public UserInfo getUserInfo(String username) {
        AuthUser user = authUserMapper.findUserByUsername(username);
        if (ObjectUtil.isNull(user)) {
            throw new BadCredentialsException(ResultCode.USER_LOGIN_ERROR.getMsg());
        }
        //角色集合
        List<AuthRole> roleList = authRoleMapper.findRoleByUserId(user.getId());
        Set<String> roles = new HashSet<>();
        if (ObjectUtil.isNull(roleList)) {
            throw new CommonException(ResultCode.USER_ROLE_NOT_EXISTS.getCode(), ResultCode.USER_ROLE_NOT_EXISTS.getMsg());
        }
        for (AuthRole role : roleList) {
            if (ObjectUtil.isNotNull(role)) {
                roles.add(role.getRoleName());
            }
        }
        /*统一赋值*/
        UserInfo userInfo = new UserInfo();
        userInfo.setAuthUser(user);
        userInfo.setAuthRoleSet(roles);
        return userInfo;
    }
}
