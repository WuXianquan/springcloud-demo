package com.study.demo.order.domain;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/11 11:21
 * @Description: 订单类
 */
@Data
@Entity(name = "t_order")
public class Order {
    @Id
    private Long id;
    private String username;
    private Integer orderStatus;
    private Timestamp createTime;
    private BigDecimal orderAmount;
    private List<OrderDetail> detailListList;
}
