package com.study.demo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Lon
 * @Date: 2019/6/11 17:22
 * @Description: 订单服务异常枚举类
 */
@Getter
@AllArgsConstructor
public enum OrderExceptionEnum {

    PRODUCT_NO_EXITS(1001, "商品不存在"),
    PRODUCT_NO_SELL(1002, "商品已下架"),
    PRODUCT_NO_ENOUGH(1003, "商品库存不足"),
    PRODUCT_AMOUNT_ERROR(1004, "商品库存异常"),
    REDUCE_USER_SOCRE_ERROR(9998, "扣减用户积分异常"),
    CREATE_ORDER_ERROR(9999, "生成订单异常");

    private Integer code;
    private String msg;
}
