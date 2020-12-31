package com.example.provider.service;

import com.example.consumer.feign.http.AjaxResult;
import com.example.consumer.feign.model.LoginParamDTO;

/**
 * @ClassName ImgCodeService
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/11/30 2:15
 * @Version 1.0
 */
public interface ImgCodeService {

    /**
     * 登录页面生成验证码
     *
     * @return
     */
    AjaxResult getImgCodeByFeignApi();

    /**
     * 校验验证码
     *
     * @return
     */
    AjaxResult validateImgCode(LoginParamDTO loginParamDTO);
}