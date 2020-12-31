package com.example.provider.service.impl;

import com.example.consumer.feign.model.EmployeeDTO;
import com.example.provider.data.mapper.EmployeeMapper;
import com.example.provider.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName EmployeeServiceImpl
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/8/29 23:59
 * @Version 1.0
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDTO> queryEmpListByFeignApi(String empName) {
        return employeeMapper.queryEmployeesByConditions(empName);
    }
}
