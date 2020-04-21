package com.study.demo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Lon
 * @Date: 2020/4/16 12:40
 * @Description: 请求返回外层参数枚举类
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum {

    SUCCESS(200, "请求成功"),
    FAIL(9999, "请求失败");

    private Integer code;
    private String msg;
}
