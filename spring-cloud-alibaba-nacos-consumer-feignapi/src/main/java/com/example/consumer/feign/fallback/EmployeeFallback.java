package com.example.consumer.feign.fallback;

import com.example.consumer.feign.EmployeeFeignService;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @ClassName EmployeeFallback
 * @User zhang
 * @Description Feign接口调用失败的回调方法
 * @Author Lucien
 * @Date 2020/8/30 0:07
 * @Version 1.0
 */
@Log4j2
@Component
public class EmployeeFallback implements FallbackFactory<EmployeeFeignService> {

    @Override
    public EmployeeFeignService create(Throwable throwable) {
        log.error("FeignLocalService接口调用异常:{}", throwable.getMessage());
        return empName -> null;
    }
}