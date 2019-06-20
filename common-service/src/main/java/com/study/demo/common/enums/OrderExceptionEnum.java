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

    product_no_exits(1001, "商品不存在"),
    product_no_sell(1002, "商品已下架"),
    product_no_enough(1003, "商品库存不足"),
    product_amount_error(1004, "商品库存异常"),
    reduce_user_socre_error(9998, "扣减用户积分异常"),
    create_order_error(9999, "生成订单异常");

    private Integer code;
    private String msg;
}
