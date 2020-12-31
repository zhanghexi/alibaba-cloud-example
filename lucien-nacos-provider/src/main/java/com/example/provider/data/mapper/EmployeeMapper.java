package com.example.provider.data.mapper;

import com.example.consumer.feign.model.EmployeeDTO;
import com.example.provider.data.model.Employee;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface EmployeeMapper extends Mapper<Employee> {

    /**
     * 根据员工姓名模糊查询员工信息
     *
     * @param empName
     * @return
     */
    List<EmployeeDTO> queryEmployeesByConditions(@Param("empName") String empName);
}