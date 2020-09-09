package com.example.consumer.feign.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName EmployeeDTO
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/8/30 0:03
 * @Version 1.0
 */
@Data
public class EmployeeDTO {

    private String name;
    private Long sex;
    private Long role;
    private Long age;
    private BigDecimal totalSalary;
}
