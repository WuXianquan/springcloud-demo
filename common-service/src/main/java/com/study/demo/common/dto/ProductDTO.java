package com.study.demo.common.dto;

import com.study.demo.common.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @Author: Lon
 * @Date: 2020/4/26 16:18
 * @Description: 商品信息传输类
 */
@Proxy(lazy = false)
@Getter
@Setter
@NoArgsConstructor
public class ProductDTO implements Serializable {

    private Long id;

    private String name;

    private BigDecimal price;

    private Integer status;

    private String createTime;

    public ProductDTO(Product product) {
        if (product != null) {
            BeanUtils.copyProperties(product, this);
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (product.getCreateTime() != null) {
                this.createTime = sdf.format(product.getCreateTime());
            }
        }
    }
}
