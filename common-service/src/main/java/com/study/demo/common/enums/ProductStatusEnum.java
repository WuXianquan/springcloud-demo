package com.study.demo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Lon
 * @Date: 2019/6/11 18:05
 * @Description: 商品状态枚举类
 */
@Getter
@AllArgsConstructor
public enum ProductStatusEnum {

    ON_LINE(1, "在架"),
    OUT_LINE(0, "下架");

    private Integer code;
    private String msg;
}
