package com.example.provider.controller;

import com.example.provider.service.NacosConfigConstantService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName TestController
 * @User zhang
 * @Description 测试打印nacos配置中心常量
 * @Author Lucien
 * @Date 2020/10/14 14:25
 * @Version 1.0
 */
@Log4j2
@RestController
@RefreshScope
public class NacosConfigConstantController {

    @Value("${nacos.constant}")
    private String nacosConstant;
    @Resource
    private NacosConfigConstantService nacosConfigConstantService;

    @GetMapping(value = "/getNacosConstant")
    public String getNacosConstant() {
        log.info("nacos配置中心共享配置信息:{}", nacosConfigConstantService.getNacosConstant(nacosConstant));
        return nacosConstant;
    }
}