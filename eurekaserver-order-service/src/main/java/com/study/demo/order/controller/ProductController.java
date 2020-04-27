package com.study.demo.order.controller;

import com.study.demo.common.dto.ProductDTO;
import com.study.demo.common.util.PageHelper;
import com.study.demo.order.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Lon
 * @Date: 2020/4/26 15:32
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping(value = "product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 获取所有商品
     *
     * @param pageHelper
     * @return
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageHelper listProduct(@RequestBody PageHelper pageHelper) {
        return productService.findProductList(pageHelper);
    }

    /**
     * 获取所有在架商品
     *
     * @param pageHelper
     * @return
     */
    @GetMapping(value = "/listOnLine", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageHelper listOnlineProduct(@RequestBody PageHelper pageHelper) {
        return productService.findOnLineProductList(pageHelper);
    }

    /**
     * 根据商品Id 获取单个商品信息，商品不存在则抛出异常
     *
     * @param productId 商品id
     * @return
     */
    @GetMapping(value = "/info/{productId}")
    public ProductDTO info(@PathVariable("productId") Long productId) {
        return productService.findProductInfoById(productId);
    }
}
