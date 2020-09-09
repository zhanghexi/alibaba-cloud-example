package com.example.provider.data.mapper;

import com.example.consumer.feign.model.EmployeeDTO;
import com.example.provider.data.model.Employee;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface EmployeeMapper extends Mapper<Employee> {
    List<EmployeeDTO> queryEmpListByFeignApi(String empName);
}