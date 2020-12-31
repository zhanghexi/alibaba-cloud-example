package com.example.provider.service.impl;

import cn.hutool.json.JSONUtil;
import com.example.consumer.feign.enums.ResultCode;
import com.example.consumer.feign.http.AjaxResult;
import com.example.consumer.feign.model.LoginParamDTO;
import com.example.provider.data.mapper.AuthUserMapper;
import com.example.provider.data.model.AuthUser;
import com.example.provider.service.ImgCodeService;
import com.example.provider.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName UserServiceImpl
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/11/30 22:34
 * @Version 1.0
 */
@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private ImgCodeService imgCodeService;
    @Resource
    private AuthUserMapper authUserMapper;

    @Override
    public AjaxResult doLogin(LoginParamDTO loginParamDTO) {
        log.info("登录入参:{}", JSONUtil.toJsonStr(loginParamDTO));
        /*首先校验验证码是否正确， 不为true则异常*/
        AjaxResult result = imgCodeService.validateImgCode(loginParamDTO);
        if (result != null && !result.get(AjaxResult.DATA_TAG).equals(true)) {
            return result;
        }
        AuthUser authUser = authUserMapper.queryUserInfo(loginParamDTO);
        if (authUser == null) {
            return AjaxResult.error(ResultCode.USER_LOGIN_ERROR.getCode(), ResultCode.USER_LOGIN_ERROR.getMsg());
        }
        return AjaxResult.success(authUser);
    }

    @Override
    public AuthUser selectUserById(Integer id) {
        return authUserMapper.selectByPrimaryKey(id);
    }
}