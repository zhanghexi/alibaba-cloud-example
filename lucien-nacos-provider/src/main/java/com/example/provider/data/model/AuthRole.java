package com.example.provider.data.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

/**
 * @describe: null
 * @create: 2020-12-01 17:30:12
 * @table: AUTH_ROLE
 * @author: Lucien
 * @version: 1.0
 */
@Data
@Table(name = "AUTH_ROLE")
public class AuthRole implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID主键
     * Column:    ID
     * Length:    0
     * DefaultValue:  无默认值
     * Nullable:  false
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色名称
     * Column:    ROLE_NAME
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "ROLE_NAME")
    private String roleName;
}