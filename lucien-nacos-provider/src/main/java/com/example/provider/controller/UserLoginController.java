package com.example.provider.controller;

import cn.hutool.json.JSONUtil;
import com.example.consumer.feign.http.AjaxResult;
import com.example.consumer.feign.model.LoginParamDTO;
import com.example.provider.data.model.AuthUser;
import com.example.provider.data.model.auth.LoginTokenUser;
import com.example.provider.service.RoleService;
import com.example.provider.service.UserService;
import com.example.provider.utils.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @ClassName UserLoginController
 * @User zhang
 * @Description 用户登录接口
 * @Author Lucien
 * @Date 2020/11/30 22:07
 * @Version 1.0
 */
@Log4j2
@RestController
public class UserLoginController {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    @PostMapping(value = "/doLogin")
    public AjaxResult doLogin(@RequestBody LoginParamDTO loginParamDTO) {
        return userService.doLogin(loginParamDTO);
    }

    @GetMapping(value = "/dev-api/getInfo")
    public AjaxResult getInfo() {
        AjaxResult result = new AjaxResult();

        LoginTokenUser loginUser = SecurityUtil.getLoginUser();
        log.info("userInfo:" + JSONUtil.toJsonStr(loginUser));
        Integer id = loginUser.getId();
        AuthUser authUser = userService.selectUserById(id);
        /*角色集合*/
        Set<String> roles = roleService.getRolesByUserId(id);
        /*TODO 未来加上permission权限集合*/
        result.put("user", authUser);
        result.put("roles", roles);
        return result;
    }
}