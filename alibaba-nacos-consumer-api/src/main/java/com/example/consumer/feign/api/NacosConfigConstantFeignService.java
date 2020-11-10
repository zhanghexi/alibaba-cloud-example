package com.example.consumer.feign.api;

import com.example.consumer.feign.fallback.NacosConfigConstantFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName NacosConfigConstantService
 * @User zhang
 * @Description 对外暴露获取nacos常量的接口
 * @Author Lucien
 * @Date 2020/10/15 11:02
 * @Version 1.0
 */
@Component
@FeignClient(contextId = "nacosConfigConstantFeignService", value = "nacos-provider",
        fallbackFactory = NacosConfigConstantFallback.class)
public interface NacosConfigConstantFeignService {

    @GetMapping(value = "/getNacosConstant")
    String getNacosConstant();
}
