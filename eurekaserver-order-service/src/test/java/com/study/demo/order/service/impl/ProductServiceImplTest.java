package com.study.demo.order.service.impl;

import com.study.demo.common.domain.Product;
import com.study.demo.common.enums.ProductStatusEnum;
import com.study.demo.common.util.PageHelper;
import com.study.demo.order.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    private static final long productId = 298614743086075904L;

    @Autowired
    private ProductService productService;

    @Test
    public void findProductList() {
        PageHelper helper = productService.findProductList(new PageHelper());
        Assert.assertNotEquals(0, helper.getSize().intValue());
    }

    @Test
    public void findProductById() {
        Product product = productService.findProductById(productId);
        Assert.assertEquals(productId, product.getId().longValue());
    }

    @Test
    public void createProduct() {
        Product product = new Product();
        product.setName("Java从入门到放弃");
        product.setPrice(new BigDecimal(79.5));
        product.setStock(30L);
        product.setFrozenStock(0L);
        product.setStatus(ProductStatusEnum.on_line.getCode());
        productService.createProduct(product);
        Assert.assertNotNull(product.getId());
    }

    @Test
    public void updateProduct() {
        Product product = productService.findProductById(productId);
        BigDecimal price = new BigDecimal(69.5);
        product.setPrice(price);
        productService.updateProduct(product);
        Assert.assertEquals(price, product.getPrice());
    }

    @Test
    public void reduceProductStock() {
        Product product = productService.findProductById(productId);// 1
        productService.reduceProductStock(productId, 5L);// 2 预扣库存
        Product newProduct = productService.findProductById(productId);// 3
        Assert.assertEquals(5L, product.getStock() - newProduct.getStock());
        Assert.assertEquals(5L, newProduct.getFrozenStock() - product.getFrozenStock());
    }

    @Test
    public void addProductStock() {
        Product product = productService.findProductById(productId);
        productService.addProductStock(productId, 5L);
        Product p = productService.findProductById(productId);
        Assert.assertEquals(5L, p.getStock() - product.getStock());
        Assert.assertEquals(5L, p.getFrozenStock() - product.getFrozenStock());
    }
}