package com.example.provider.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 * @describe: 员工信息表
 * @create: 2020-08-29 23:51:01
 * @table: EMPLOYEE
 * @author: Lucien
 * @version: 1.0
 */
@Table(name = "EMPLOYEE")
@Data
public class Employee implements Serializable {
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
     * 性别（1男，2女）
     * Column:    EMP_SEX
     * Length:    11
     * DefaultValue:  NULL 
     * Nullable:  true
     */
    @Column(name = "EMP_SEX")
    private Long empSex;

    /**
     * 员工类别（1普通员工、2组长、3经理、4总监）
     * Column:    EMP_ROLE
     * Length:    11
     * DefaultValue:  NULL 
     * Nullable:  true
     */
    @Column(name = "EMP_ROLE")
    private Long empRole;

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

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", empName=").append(empName);
        sb.append(", empSex=").append(empSex);
        sb.append(", empRole=").append(empRole);
        sb.append(", empSalary=").append(empSalary);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", empAge=").append(empAge);
        sb.append("]");
        return sb.toString();
    }
}