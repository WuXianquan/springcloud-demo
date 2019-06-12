package com.study.demo.order.domain;

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
 * @Description: 订单类
 */
@Proxy(lazy = false)
@Getter
@Setter
@Entity(name = "t_order")
public class Order {
    @Id
    @Column(unique = true)
    private Long id;
    private Long userId;
    private Integer orderStatus;
    private Timestamp createTime;
    private BigDecimal orderAmount;

    @OneToMany(targetEntity = OrderDetail.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId")
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
