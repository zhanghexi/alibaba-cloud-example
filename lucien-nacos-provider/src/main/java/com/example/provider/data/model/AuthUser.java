package com.example.provider.data.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

/**
 * @describe: null
 * @create: 2020-12-01 17:27:46
 * @table: AUTH_USER
 * @author: Lucien
 * @version: 1.0
 */
@Data
@Table(name = "AUTH_USER")
public class AuthUser implements Serializable {

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
     * 用户账号
     * Column:    USERNAME
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "USERNAME")
    private String username;

    /**
     * 密码
     * Column:    PASSWORD
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 头像地址
     */
    @Column(name = "AVATAR")
    private String avatar;
}