package com.example.provider.data.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

/**
 * @describe: null
 * @create: 2020-11-30 15:39:38
 * @table: ROLE
 * @author: Lucien
 * @version: 1.0
 */
@Data
@Table(name = "ROLE")
public class Role implements Serializable {

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
     * 角色名称
     * Column:    ROLE_NAME
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "ROLE_NAME")
    private String roleName;

    /**
     * 角色编号
     * Column:    ROLE_CODE
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "ROLE_CODE")
    private String roleCode;
}