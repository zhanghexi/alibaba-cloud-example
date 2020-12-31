package com.example.consumer.feign.api;

import com.example.consumer.feign.fallback.UserInfoFallback;
import com.example.consumer.feign.http.AjaxResult;
import com.example.consumer.feign.model.LoginParamDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName RemoteUserInfoService
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/11/30 22:59
 * @Version 1.0
 */
@FeignClient(contextId = "remoteUserInfoService", value = "nacos-provider",
        fallbackFactory = UserInfoFallback.class)
public interface RemoteUserInfoService {

    @PostMapping(value = "/doLogin")
    AjaxResult doLogin(@RequestBody LoginParamDTO loginParamDTO);
}