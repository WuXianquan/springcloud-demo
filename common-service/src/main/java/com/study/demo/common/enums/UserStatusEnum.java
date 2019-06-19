package com.study.demo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Lon
 * @Date: 2019/6/18 16:49
 * @Description: 用户状态枚举类
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    DELETE(-1, "已注销"),
    frozen(0, "冻结"),
    NORMAL(1, "正常");

    private Integer code;
    private String msg;
}
