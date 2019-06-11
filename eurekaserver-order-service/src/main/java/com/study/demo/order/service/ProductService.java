package com.study.demo.order.service;

import com.study.demo.order.domain.Product;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/11 16:29
 * @Description: 商品接口
 */
public interface ProductService {

    List<Product> findProductList();

    Product findProductById(Long productId);
}
