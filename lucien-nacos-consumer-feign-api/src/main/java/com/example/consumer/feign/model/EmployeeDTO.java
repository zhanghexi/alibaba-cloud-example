package com.example.consumer.feign.model;

import lombok.Data;

import java.io.Serializable;
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
public class EmployeeDTO implements Serializable {

    /**
     * 员工姓名
     */
    private String empName;
    /**
     * 性别
     */
    private String empSex;
    /**
     * 年龄
     */
    private Long empAge;
    /**
     * 邮箱
     */
    private String empEmail;
    /**
     * 地址
     */
    private String empAddress;
    /**
     * 薪资
     */
    private BigDecimal empSalary;
    /**
     * 角色名称
     */
    private String deptName;
    /**
     * 角色名称
     */
    private String roleName;
}
