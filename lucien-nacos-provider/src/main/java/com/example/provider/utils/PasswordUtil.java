package com.example.provider.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName PasswordUtil
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/12/9 14:45
 * @Version 1.0
 */
public class PasswordUtil {

    public static boolean encryptAndDecryptPwd(String pwd) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptPwd = encoder.encode(pwd);
        System.out.println("加密后的密码:" + encryptPwd);
        if (encoder.matches(pwd, encryptPwd)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(PasswordUtil.encryptAndDecryptPwd("909090"));
    }
}
