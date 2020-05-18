package com.study.demo.common.util;

import org.springframework.util.DigestUtils;

public class MD5 {

    private static final String slat = "&%5123***&&%%$$#@";//盐，用于混交md5

    /**
     * MD5加密
     *
     * @param str 加密对象
     * @return
     */
    public static String getMD5(String str) {
        String base = str + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}