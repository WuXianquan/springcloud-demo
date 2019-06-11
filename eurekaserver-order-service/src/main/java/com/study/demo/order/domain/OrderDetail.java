package com.study.demo.order.domain;

import lombok.Data;
import java.math.BigDecimal;

/**
 * @Author: Lon
 * @Date: 2019/6/11 11:29
 * @Description: 订单明细
 */
@Data
public class OrderDetail {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer productNumber;
    private BigDecimal productPrice;
    private BigDecimal amount;
}
