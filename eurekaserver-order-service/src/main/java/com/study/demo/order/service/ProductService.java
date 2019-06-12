package com.study.demo.order.service;

import com.common.domain.Product;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/11 16:29
 * @Description: 商品接口
 */
public interface ProductService {

    List<Product> findProductList();

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
