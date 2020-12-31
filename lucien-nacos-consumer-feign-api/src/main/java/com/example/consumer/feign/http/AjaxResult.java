package com.example.consumer.feign.http;

import cn.hutool.http.HttpStatus;

import java.util.HashMap;

/**
 * @ClassName AjaxResult
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/11/30 23:46
 * @Version 1.0
 */
public class AjaxResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;
    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";
    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";
    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    /**
     * 无参构造器
     */
    public AjaxResult() {
    }

    /**
     * 构建没有数据的AjaxResult对象
     *
     * @param code
     * @param message
     */
    public AjaxResult(int code, String message) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, message);
    }

    /**
     * 构建带数据的AjaxResult对象
     *
     * @param code
     * @param message
     * @param data
     */
    public AjaxResult(int code, String message, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, message);
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }

    public static AjaxResult success() {
        return AjaxResult.success(HttpStatus.HTTP_OK, "成功", null);
    }

    public static AjaxResult success(Object data) {
        return AjaxResult.success(HttpStatus.HTTP_OK, "成功", data);
    }

    public static AjaxResult success(int code, String message, Object data) {
        return new AjaxResult(code, message, data);
    }

    /**
     * 返回系统异常
     *
     * @return
     */
    public static AjaxResult sysError() {
        return new AjaxResult(HttpStatus.HTTP_INTERNAL_ERROR, "系统异常");
    }

    /**
     * 返回业务异常信息
     *
     * @param code
     * @param message
     * @return
     */
    public static AjaxResult error(int code, String message) {
        return new AjaxResult(code, message);
    }
}