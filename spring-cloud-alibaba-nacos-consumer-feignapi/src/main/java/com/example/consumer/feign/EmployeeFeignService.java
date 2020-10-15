package com.example.consumer.feign;

import com.example.consumer.feign.fallback.EmployeeFallback;
import com.example.consumer.feign.model.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @ClassName EmployeeService
 * @User zhang
 * @Description 对外暴露的Feign接口
 * @Author Lucien
 * @Date 2020/8/29 23:52
 * @Version 1.0
 */
@Component
@FeignClient(contextId = "employeeFeignService", value = "nacos-provider",
        fallbackFactory = EmployeeFallback.class)
public interface EmployeeFeignService {

    @GetMapping(value = "/emp/queryEmpListByFeignApi/{empName}")
    List<EmployeeDTO> queryEmpListByFeignApi(@PathVariable("empName") String empName);
}
