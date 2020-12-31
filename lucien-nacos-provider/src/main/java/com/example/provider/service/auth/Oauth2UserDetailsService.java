package com.example.provider.service.auth;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.example.provider.data.model.AuthUser;
import com.example.provider.data.model.auth.LoginTokenUser;
import com.example.provider.data.model.auth.UserInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @ClassName JdbcUserDetails
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/12/1 17:06
 * @Version 1.0
 */
@Log4j2
@Service
public class Oauth2UserDetailsService implements UserDetailsService {

    @Resource
    private UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (ObjectUtil.isNull(username)) {
            throw new UsernameNotFoundException("User '" + username + "' can not be found!");
        }
        UserInfo userInfo = userInfoService.getUserInfo(username);
        return getUserDetails(userInfo);
    }

    private UserDetails getUserDetails(UserInfo userInfo) {
        if (ObjectUtil.isNotNull(userInfo) && CollectionUtil.isNotEmpty(userInfo.getAuthRoleSet())) {
            /*获取用户信息*/
            AuthUser user = userInfo.getAuthUser();
            /*获取角色*/
            Set<String> roles = userInfo.getAuthRoleSet();

            List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
            /*新建与userDetails相关的用户信息*/
            LoginTokenUser loginTokenUser = new LoginTokenUser(user.getId(), user.getUsername(), user.getPassword(),
                    true, true, true, true, authorityList);
            log.info("返回的userDetail信息:\n{}", JSONUtil.toJsonPrettyStr(loginTokenUser));
            return loginTokenUser;
        }
        return null;
    }
}
