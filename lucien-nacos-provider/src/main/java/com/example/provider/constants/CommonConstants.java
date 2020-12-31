package com.example.provider.constants;

/**
 * @ClassName Constants
 * @User zhang
 * @Description 公共常量
 * @Author Lucien
 * @Date 2020/11/30 2:21
 * @Version 1.0
 */
public class CommonConstants {

    /**
     * 存入redis的auth前缀（*号必须要加，否则无法模糊查询）
     */
    public static final String AUTH_PREFIX = "auth_to_access*";
    /**
     * 存入redis的验证码key前缀
     */
    public static final String CODE_KEY = "img_code_";
    /**
     * 验证码过期时间(秒为单位)
     */
    public static final int EXPIRATION_TIME = 180;
    /**
     * 头像url前缀
     */
    public static final String AVATAR_PREFIX = "/user/avatar/";
}