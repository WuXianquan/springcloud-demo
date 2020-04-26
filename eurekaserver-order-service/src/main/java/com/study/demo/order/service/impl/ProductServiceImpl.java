package com.study.demo.order.service.impl;

import com.study.demo.common.domain.Product;
import com.study.demo.common.dto.ProductDTO;
import com.study.demo.common.enums.OrderExceptionEnum;
import com.study.demo.common.exception.ServiceException;
import com.study.demo.common.util.IDGenerator;
import com.study.demo.common.util.PageHelper;
import com.study.demo.order.repository.ProductRepository;
import com.study.demo.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author: Lon
 * @Date: 2019/6/11 16:41
 * @Description: 商品服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public PageHelper findProductList(PageHelper pageHelper) {
        Page<Product> products = productRepository.findAll(pageHelper.pageable());
        List<ProductDTO> productDTOS = new ArrayList<>();
        products.getContent().forEach(product -> {
            productDTOS.add(new ProductDTO(product));
        });
        pageHelper.queryResult(products);
        pageHelper.setData(productDTOS);
        return pageHelper;
    }

    @Override
    public Product findProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.isPresent() ? product.get() : null;
    }

    @Override
    public Product createProduct(Product product) {
        product.setId(IDGenerator.getInstance().next());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public int reduceProductStock(Long productId, Long amount) {
        Product product = productRepository.getOne(productId);
        if (product == null) {
            throw new ServiceException(OrderExceptionEnum.product_no_exits.getCode(), OrderExceptionEnum.product_no_exits.getMsg());
        }
        if (product.getStock().longValue() < amount.longValue()) {
            throw new ServiceException(OrderExceptionEnum.product_no_enough.getCode(), OrderExceptionEnum.product_no_enough.getMsg());
        }
        return productRepository.reduceProductStock(productId, amount);
    }

    @Override
    public int addProductStock(Long productId, Long amount) {
        Product product = productRepository.getOne(productId);
        if (product == null) {
            throw new ServiceException(OrderExceptionEnum.product_no_exits.getCode(), OrderExceptionEnum.product_no_exits.getMsg());
        }
        if (product.getFrozenStock().longValue() < amount.longValue()) {
            throw new ServiceException(OrderExceptionEnum.product_amount_error.getCode(), OrderExceptionEnum.product_amount_error.getMsg());
        }
        return productRepository.addProductStock(productId, amount);
    }
}
