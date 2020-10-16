package com.example.provider.data.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;

/**
 * @describe: 日志记录表
 * @create: 2020-10-16 16:09:53
 * @table: OPERATION
 * @author: Lucien
 * @version: 1.0
 */
@Data
@Table(name = "OPERATION")
public class Operation implements Serializable {

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
     * 客户端ip
     * Column:    CLIENT_IP
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "CLIENT_IP")
    private String clientIp;

    /**
     * 操作人姓名
     * Column:    USERNAME
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "USERNAME")
    private String username;

    /**
     * 日志类型
     * Column:    OPER_TYPE
     * Length:    11
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "OPER_TYPE")
    private Long operType;

    /**
     * 操作的url
     * Column:    OPER_URL
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "OPER_URL")
    private String operUrl;

    /**
     * 操作事件
     * Column:    OPER_EVENT
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "OPER_EVENT")
    private String operEvent;

    /**
     * 请求参数信息
     * Column:    REQ_PARAM
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "REQ_PARAM")
    private String reqParam;

    /**
     * 请求方式：POST或者GET
     * Column:    REQ_TYPE
     * Length:    255
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "REQ_TYPE")
    private String reqType;

    /**
     * 操作时间
     * Column:    OPER_TIME
     * Length:    7
     * DefaultValue:  无默认值
     * Nullable:  true
     */
    @Column(name = "OPER_TIME")
    private Date operTime;
}