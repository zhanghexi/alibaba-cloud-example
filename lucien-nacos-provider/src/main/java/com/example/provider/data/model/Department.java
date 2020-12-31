package com.example.provider.data.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

/**
 * @describe: null
 * @create: 2020-11-30 15:39:23
 * @table: DEPARTMENT
 * @author: Lucien
 * @version: 1.0
 */
@Data
@Table(name = "DEPARTMENT")
public class Department implements Serializable {

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
     * 部门名称
     * Column:    DEP_NAME
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "DEP_NAME")
    private String depName;

    /**
     * 部门编号
     * Column:    DEP_CODE
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "DEP_CODE")
    private String depCode;

    /**
     * 备注
     * Column:    REMARK
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "REMARK")
    private String remark;
}