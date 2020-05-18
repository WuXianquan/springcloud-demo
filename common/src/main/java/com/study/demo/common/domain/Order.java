package com.study.demo.common.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/11 11:21
 * @Description: 订单基础类
 */
@Proxy(lazy = false)
@Getter
@Setter
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @Column(unique = true)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_status")
    private Integer orderStatus;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    @OneToMany(targetEntity = OrderDetail.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderDetail> detailListList;

    public Timestamp getCreateTime() {
        if (createTime == null) {
            return null;
        }
        return (Timestamp) createTime.clone();
    }

    public void setCreateTime(Timestamp createTime) {
        if (createTime == null) {
            this.createTime = null;
        } else {
            this.createTime = (Timestamp) createTime.clone();
        }
    }
}
