package com.example.consumer.feign.api;

import com.example.consumer.feign.fallback.ImgCodeFallback;
import com.example.consumer.feign.http.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName ImgCodeService
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/11/30 2:08
 * @Version 1.0
 */
@FeignClient(contextId = "remoteImgCodeService", value = "nacos-provider",
        fallbackFactory = ImgCodeFallback.class)
public interface RemoteImgCodeService {

    @GetMapping(value = "/getImgCode")
    AjaxResult getImgCodeByFeignApi();
}