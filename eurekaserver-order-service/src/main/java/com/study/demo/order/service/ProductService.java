package com.study.demo.order.service;

import com.study.demo.common.domain.Product;
import com.study.demo.common.dto.ProductDTO;
import com.study.demo.common.util.PageHelper;

/**
 * @Author: Lon
 * @Date: 2019/6/11 16:29
 * @Description: 商品接口
 */
public interface ProductService {

    /**
     * 分页查询所有商品列表
     * @param pageHelper
     * @return
     */
    PageHelper findProductList(PageHelper pageHelper);

    /**
     * 分页查询在家商品列表
     * @param pageHelper
     * @return
     */
    PageHelper findOnLineProductList(PageHelper pageHelper);

    /**
     * 根据商品ID查询单个商品，商品不存在返回null
     * @param productId
     * @return
     */
    Product findProductById(Long productId);

    /**
     * 根据商品ID查询单个商品，商品不存在抛出异常
     * @param productId
     * @return
     */
    ProductDTO findProductInfoById(Long productId);

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
