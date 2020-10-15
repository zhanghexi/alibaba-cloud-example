package com.example.consumer.feign.fallback;

import com.example.consumer.feign.api.NacosConfigConstantFeignService;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @ClassName NacosConfigConstantFallback
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/10/15 11:03
 * @Version 1.0
 */
@Log4j2
@Component
public class NacosConfigConstantFallback implements FallbackFactory<NacosConfigConstantFeignService> {

    @Override
    public NacosConfigConstantFeignService create(Throwable throwable) {
        log.error("NacosConfigConstantFeignService接口调用异常:{}", throwable.getMessage());
        return () -> null;
    }
}