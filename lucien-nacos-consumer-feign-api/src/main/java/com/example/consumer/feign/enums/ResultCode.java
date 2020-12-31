package com.example.consumer.feign.enums;

import lombok.Getter;

/**
 * @ClassName ResultCode
 * @User zhang
 * @Description 接口返回状态码、具体信息枚举类
 * @Author Lucien
 * @Date 2020/11/19 0:44
 * @Version 1.0
 */
@Getter
public enum ResultCode {
    /*成功状态码*/
    SUCCESS(0, "成功"),
    /*参数错误*/
    PARAM_IS_EMPTY(1001, "参数为空"),
    PARAM_ERROR(1002, "参数错误"),
    /*用户级别错误*/
    USER_LOGIN_ERROR(2001, "账号不存在或密码错误"),
    USER_NOT_EXIST(2002, "用户不存在"),
    USER_HAS_EXISTED(2003, "用户已存在"),

    /*ERROR_USER_NOT_EXISTS(1001, "用户不存在!"),*/
    USER_ROLE_NOT_EXISTS(2004, "用户与之对应的角色不存在!"),

    /*其他异常*/
    IMAGE_CODE_ERROR(3001, "验证码生成异常"),
    IMAGE_CODE_NOT_EMPTY(3002, "验证码不能为空"),
    IMAGE_CODE_EXPIRED(3003, "验证码已失效"),
    IMAGE_INPUT_ERROR(3004, "验证码输入错误"),


    UNAUTHORIZED(401, "暂未登录或token已过期"),
    /*INVALID_TOKEN(401, "token已失效"),*/
    FORBIDDEN(403, "权限不足"),

    CLIENT_PARAM_MISSING(4001, "客户端信息缺失"),
    CLIENT_AUTH_FAIL(4002, "客户端认证失败"),

    /*AUTH_TOKEN_INVALID(5001, "Auth请求头非法"),
    AUTH_TOKEN_NULL(5002, "Auth请求头为空"),*/

    UPLOAD_FAIL(5001, "上传失败"),

    /*系统异常*/
    SYSTEM_ERROR(-1, "系统异常");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}