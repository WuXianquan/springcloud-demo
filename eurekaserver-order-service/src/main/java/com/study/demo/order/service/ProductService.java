package com.study.demo.order.service;

import com.study.demo.common.domain.Product;
import com.study.demo.common.util.PageHelper;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/11 16:29
 * @Description: 商品接口
 */
public interface ProductService {

    PageHelper findProductList(PageHelper pageHelper);

    Product findProductById(Long productId);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    /**
     * 预扣商品库存
     * @param productId 商品ID
     * @param amount    预扣数量
     * @return
     */
    int reduceProductStock(Long productId, Long amount);

    /**
     * 预充商品库存
     * @param productId
     * @param amount
     * @return
     */
    int addProductStock(Long productId, Long amount);
}
