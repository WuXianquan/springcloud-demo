package com.study.demo.common.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Author: Lon
 * @Date: 2019/6/11 11:29
 * @Description: 订单明细基础类
 */
@Proxy(lazy = false)
@Getter
@Setter
@Entity
@Table(name = "t_order_detail")
public class OrderDetail {
    @Id
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_number")
    private Integer productNumber;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "amount")
    private BigDecimal amount;
}
