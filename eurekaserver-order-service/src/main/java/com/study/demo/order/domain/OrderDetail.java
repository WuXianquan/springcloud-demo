package com.study.demo.order.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @Author: Lon
 * @Date: 2019/6/11 11:29
 * @Description: 订单明细
 */
@Proxy(lazy = false)
@Getter
@Setter
@Entity(name = "t_order_detail")
public class OrderDetail {
    @Id
    @Column(unique = true)
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer productNumber;
    private BigDecimal productPrice;
    private BigDecimal amount;
}
