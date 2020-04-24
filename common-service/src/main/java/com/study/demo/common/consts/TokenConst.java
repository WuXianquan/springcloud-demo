package com.study.demo.common.consts;

/**
 * @Author: Lon
 * @Date: 2020/4/21 15:07
 * @Description: Token相关的常量类
 */
public class TokenConst {

    /**
     * token在HTTP请求头里面的参数名
     */
    public static final String TOKEN_HEAD = "token";

    /**
     * token的redis key前缀(Id)
     */
    public static final String TOKEN_REDISKEY_PRE_ID = "user:token:id:";

    /**
     * token的redis key前缀(username)
     */
    public static final String TOKEN_REDISKEY_PRE_USERNAME = "user:token:username:";

    /**
     * token的redis key有效时间，单位秒
     */
    public static final Long TOKEN_REDISKEY_EXPIRETIME = 7200L;
}
