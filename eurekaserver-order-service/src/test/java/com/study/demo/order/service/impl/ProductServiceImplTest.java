package com.study.demo.order.service.impl;

import com.study.demo.order.domain.Product;
import com.study.demo.order.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findProductList() {
        List<Product> products = productService.findProductList();
        Assert.assertNotNull(products);
    }

    @Test
    public void findProductById() {
    }

    @Test
    public void createProduct() {
    }

    @Test
    public void updateProduct() {
    }

    @Test
    public void reduceProductStock() {
    }

    @Test
    public void addProductStock() {
    }
}