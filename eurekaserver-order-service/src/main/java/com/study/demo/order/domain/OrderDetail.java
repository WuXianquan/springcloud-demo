package com.study.demo.order.domain;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @Author: Lon
 * @Date: 2019/6/11 11:29
 * @Description: 订单明细
 */
@Data
@Entity(name = "t_oder_detail")
public class OrderDetail {
    @Id
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer productNumber;
    private BigDecimal productPrice;
    private BigDecimal amount;
}
