package com.study.demo.common.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author: Lon
 * @Date: 2020/5/15 9:53
 * @Description: 购物车子项类
 */
@Getter
@Setter
@Entity
@Table(name = "t_cart_item")
public class CartItem {

    @Id
    @Column(name = "id", unique = true)
    private Long itemId;

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "num")
    private Integer productNum;

    @Column(name = "create_time")
    private Timestamp createTime;
}
