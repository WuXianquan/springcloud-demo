package com.study.demo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Lon
 * @Date: 2020/5/15 11:09
 * @Description: 商品库存状态枚举类
 */
@Getter
@AllArgsConstructor
public enum ProductSellStatusEnum {

    SELL_IN(1,"有货"),
    SELL_OUT(0,"无货"),
    ;

    private Integer code;
    private String msg;
}
