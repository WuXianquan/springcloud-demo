package com.study.demo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Lon
 * @Date: 2019/6/18 17:16
 * @Description: 用户服务异常枚举类
 */
@Getter
@AllArgsConstructor
public enum  UserExceptionEnum {

    USER_NO_EXITS(1001, "用户不存在"),
    USER_IS_DELETE(1002, "用户已注销"),
    USER_IS_FROZEN(1003, "用户已被冻结"),
    PASSWORD_ERROR(1004, "密码错误");

    private Integer code;
    private String msg;
}
