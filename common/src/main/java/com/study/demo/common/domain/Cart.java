package com.study.demo.common.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2020/5/15 10:13
 * @Description: 购物车类
 */
@Getter
@Setter
@Entity
@Table(name = "t_cart")
public class Cart {

    @Id
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(targetEntity = CartItem.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    @OrderBy("createTime desc ")
    private List<CartItem> cartItems;

    @Column(name = "create_time")
    private Timestamp createTime;
}
