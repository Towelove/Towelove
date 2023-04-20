package com.towelove.monitor.test.security;

import com.towelove.common.security.utils.SecurityUtils;

/**
 * @author: 张锦标
 * @date: 2023/4/20 21:29
 * EncryptTest类
 */
public class EncryptTest {
    public static void main(String[] args) {
        String s = "123123123";
        String password = SecurityUtils.encryptPassword(s);
        System.out.println(password);
        System.out.println(SecurityUtils.matchesPassword(s, password));
    }
}
