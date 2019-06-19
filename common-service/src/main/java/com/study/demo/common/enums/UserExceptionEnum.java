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

    user_no_exits(1001, "用户不存在"),
    user_is_delete(1002, "用户已注销"),
    user_is_frozen(1003, "用户已被冻结");

    private Integer code;
    private String msg;
}
