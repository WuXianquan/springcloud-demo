package com.study.demo.order.domain;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author: Lon
 * @Date: 2019/6/11 11:23
 * @Description: 商品类
 */
@Data
@Entity(name = "t_product")
public class Product {
    @Id
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer status;
    private Long stock;// 库存数量
    private Long frozenStock;// 冻结数量
    private Timestamp createTime;
}
