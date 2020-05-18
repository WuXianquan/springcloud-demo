package com.study.demo.common.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author: Lon
 * @Date: 2019/6/11 11:23
 * @Description: 商品基础类
 */
@Proxy(lazy = false)
@Getter
@Setter
@Entity
@Table(name = "t_product")
public class Product {
    @Id
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private Integer status;

    @Column(name = "stock")
    private Long stock;// 库存数量

    @Column(name = "frozen_stock")
    private Long frozenStock;// 冻结数量

    @Column(name = "create_time")
    private Timestamp createTime;

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
