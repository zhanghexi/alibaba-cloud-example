package com.example.provider.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 * @describe: 员工信息表
 * @create: 2020-11-30 15:41:54
 * @table: EMPLOYEE
 * @author: Lucien
 * @version: 1.0
 */
@Data
@Table(name = "EMPLOYEE")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID主键
     * Column:    ID
     * Length:    11
     * DefaultValue:  无默认值
     * Nullable:  false
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 员工姓名
     * Column:    EMP_NAME
     * Length:    255
     * DefaultValue:  NULL 
     * Nullable:  true
     */
    @Column(name = "EMP_NAME")
    private String empName;

    /**
     * 员工性别（1男，2女）
     * Column:    EMP_SEX
     * Length:    11
     * DefaultValue:  NULL 
     * Nullable:  true
     */
    @Column(name = "EMP_SEX")
    private Long empSex;

    /**
     * 薪资（保留两位小数）
     * Column:    EMP_SALARY
     * Length:    11
     * DefaultValue:  NULL 
     * Nullable:  true
     */
    @Column(name = "EMP_SALARY")
    private BigDecimal empSalary;

    /**
     * 创建时间（默认为sysdate，添加的时候不用手动操作）
     * Column:    CREATE_TIME
     * Length:    7
     * DefaultValue:  sysdate 
     * Nullable:  true
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 修改时间（已添加触发器，在update的时候不用手动更新）
     * Column:    UPDATE_TIME
     * Length:    7
     * DefaultValue:  NULL 
     * Nullable:  true
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 员工年龄
     * Column:    EMP_AGE
     * Length:    11
     * DefaultValue:  NULL 
     * Nullable:  true
     */
    @Column(name = "EMP_AGE")
    private Long empAge;

    /**
     * 员工编号
     * Column:    EMP_CODE
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "EMP_CODE")
    private String empCode;

    /**
     * 员工住址
     * Column:    EMP_ADDRESS
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "EMP_ADDRESS")
    private String empAddress;

    /**
     * 员工邮箱
     * Column:    EMP_EMAIL
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "EMP_EMAIL")
    private String empEmail;

    /**
     * 角色编号
     * Column:    ROLE_CODE
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "ROLE_CODE")
    private String roleCode;

    /**
     * 部门编号
     * Column:    DEP_CODE
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "DEP_CODE")
    private String depCode;
}