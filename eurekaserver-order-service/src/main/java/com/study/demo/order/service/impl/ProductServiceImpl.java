package com.study.demo.order.service.impl;

import com.study.demo.order.domain.Product;
import com.study.demo.order.repository.ProductRepository;
import com.study.demo.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/11 16:41
 * @Description:
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findProductList() {
        return  productRepository.findAll();
    }

    @Override
    public Product findProductById(Long productId) {
        return productRepository.getOne(productId);
    }
}
