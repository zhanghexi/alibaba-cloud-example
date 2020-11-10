package com.example.provider.service;

import com.example.consumer.feign.model.EmployeeDTO;

import java.util.List;

/**
 * @ClassName EmployeeService
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/8/29 23:57
 * @Version 1.0
 */
public interface EmployeeService {

    /**
     * 条件查询
     *
     * @return
     */
    List<EmployeeDTO> queryEmpListByFeignApi(String empName);
}
