package com.example.provider.controller;

import com.example.consumer.feign.http.AjaxResult;
import com.example.provider.service.ImgCodeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName ImgCodeController
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/11/30 2:13
 * @Version 1.0
 */
@Log4j2
@RestController
@RequestMapping(value = "/dev-api")
public class ImgCodeController {

    @Resource
    private ImgCodeService imgCodeService;

    @GetMapping(value = "/getImgCode")
    public AjaxResult getImgCode() {
        return imgCodeService.getImgCodeByFeignApi();
    }
}