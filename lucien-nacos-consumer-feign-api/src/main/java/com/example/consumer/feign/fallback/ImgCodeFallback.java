package com.example.consumer.feign.fallback;

import com.example.consumer.feign.api.RemoteImgCodeService;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @ClassName ImgCodeFallback
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/11/30 2:09
 * @Version 1.0
 */
@Log4j2
@Component
public class ImgCodeFallback implements FallbackFactory<RemoteImgCodeService> {

    @Override
    public RemoteImgCodeService create(Throwable throwable) {
        log.error("获取验证码接口调用异常:{}", throwable.getMessage());
        return null;
    }
}