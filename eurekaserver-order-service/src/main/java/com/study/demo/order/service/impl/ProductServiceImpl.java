package com.study.demo.order.service.impl;

import com.study.demo.common.domain.Product;
import com.study.demo.common.dto.ProductDTO;
import com.study.demo.common.enums.OrderExceptionEnum;
import com.study.demo.common.enums.ProductStatusEnum;
import com.study.demo.common.exception.ServiceException;
import com.study.demo.common.util.IDGenerator;
import com.study.demo.common.util.PageHelper;
import com.study.demo.order.repository.ProductRepository;
import com.study.demo.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Page<Product> products = productRepository.findAll(pageHelper.pageable(sort));
        List<ProductDTO> productDTOS = new ArrayList<>();
        products.getContent().forEach(product -> productDTOS.add(new ProductDTO(product)));
        pageHelper.queryResult(products);
        pageHelper.setData(productDTOS);
        return pageHelper;
    }

    @Override
    public PageHelper findOnLineProductList(PageHelper pageHelper) {
        Pageable pageable = pageHelper.pageable(new Sort(Sort.Direction.DESC, "createTime"));

        Specification<Product> specification = (Specification<Product>) (root, cq, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.equal(root.get("status").as(Integer.class), ProductStatusEnum.ON_LINE.getCode().intValue()));
            return cb.and(list.toArray(new Predicate[list.size()]));
        };
        Page<Product> products = productRepository.findAll(specification, pageable);
        List<ProductDTO> productDTOS = new ArrayList<>();
        products.getContent().forEach(product -> productDTOS.add(new ProductDTO(product)));
        pageHelper.queryResult(products);
        pageHelper.setData(productDTOS);
        return pageHelper;
    }

    @Override
    public ProductDTO findProductInfoById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ServiceException(OrderExceptionEnum.PRODUCT_NO_EXITS.getCode(),
                    OrderExceptionEnum.PRODUCT_NO_EXITS.getMsg());
        }
        return new ProductDTO(product.get());
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
            throw new ServiceException(OrderExceptionEnum.PRODUCT_NO_EXITS.getCode(), OrderExceptionEnum.PRODUCT_NO_EXITS.getMsg());
        }
        if (product.getStock().longValue() < amount.longValue()) {
            throw new ServiceException(OrderExceptionEnum.PRODUCT_NO_ENOUGH.getCode(), OrderExceptionEnum.PRODUCT_NO_ENOUGH.getMsg());
        }
        return productRepository.reduceProductStock(productId, amount);
    }

    @Override
    public int addProductStock(Long productId, Long amount) {
        Product product = productRepository.getOne(productId);
        if (product == null) {
            throw new ServiceException(OrderExceptionEnum.PRODUCT_NO_EXITS.getCode(), OrderExceptionEnum.PRODUCT_NO_EXITS.getMsg());
        }
        if (product.getFrozenStock().longValue() < amount.longValue()) {
            throw new ServiceException(OrderExceptionEnum.PRODUCT_AMOUNT_ERROR.getCode(), OrderExceptionEnum.PRODUCT_AMOUNT_ERROR.getMsg());
        }
        return productRepository.addProductStock(productId, amount);
    }
}
