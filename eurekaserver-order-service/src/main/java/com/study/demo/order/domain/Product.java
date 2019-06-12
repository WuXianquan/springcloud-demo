package com.study.demo.order.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author: Lon
 * @Date: 2019/6/11 11:23
 * @Description: 商品类
 */
@Proxy(lazy = false)
@Getter
@Setter
@Entity(name = "t_product")
public class Product {
    @Id
    @Column(unique = true)
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer status;
    private Long stock;// 库存数量
    private Long frozenStock;// 冻结数量
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
