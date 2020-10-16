package com.example.provider.controller;

import com.alibaba.fastjson.JSON;
import com.example.consumer.feign.model.EmployeeDTO;
import com.example.provider.config.server.ServerConfig;
import com.example.provider.service.EmployeeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName EmployeeController
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/8/30 0:10
 * @Version 1.0
 */
@Log4j2
@RestController
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;
    @Resource
    private ServerConfig serverConfig;

    @GetMapping(value = "/emp/queryEmpListByFeignApi/{empName}")
    public List<EmployeeDTO> queryEmpListByFeignApi(@PathVariable("empName") String empName) {
        List<EmployeeDTO> employeeDTOS = employeeService.queryEmpListByFeignApi(empName);
        log.info("返回值:{}", JSON.toJSONString(employeeDTOS));
        log.info("完整请求地址:{}", serverConfig.getTotalUrl());
        return employeeDTOS;
    }
}
