package com.study.demo.order.controller;

import com.study.demo.common.util.PageHelper;
import com.study.demo.order.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/list")
    public PageHelper listProduct(@RequestBody PageHelper pageHelper) {
        return productService.findProductList(pageHelper);
    }
}
