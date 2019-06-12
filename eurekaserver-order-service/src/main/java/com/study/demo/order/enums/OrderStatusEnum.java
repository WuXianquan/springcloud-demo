package com.study.demo.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Lon
 * @Date: 2019/6/12 0:02
 * @Description: 订单状态枚举类
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    NEW(0, "已下单");

    private Integer code;
    private String msg;
}
