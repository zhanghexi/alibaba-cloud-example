package com.example.consumer.feign.fallback;

import com.example.consumer.feign.api.RemoteUserInfoService;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserInfoFallback
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/11/30 22:59
 * @Version 1.0
 */
@Log4j2
@Component
public class UserInfoFallback implements FallbackFactory<RemoteUserInfoService> {

    @Override
    public RemoteUserInfoService create(Throwable throwable) {
        log.error("用户登录接口调用异常:{}", throwable.getMessage());
        return null;
    }
}