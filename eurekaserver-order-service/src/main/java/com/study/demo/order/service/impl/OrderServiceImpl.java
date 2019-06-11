package com.study.demo.order.service.impl;

import com.common.exception.ServiceException;
import com.common.util.IDGenerator;
import com.study.demo.order.domain.Order;
import com.study.demo.order.domain.OrderDetail;
import com.study.demo.order.domain.Product;
import com.study.demo.order.enums.OrderExceptionEnum;
import com.study.demo.order.enums.ProductStatusEnum;
import com.study.demo.order.service.OrderService;
import com.study.demo.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/4 15:36
 * @Description: 订单服务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Override
    public List<Order> findUserAllOrderInfo(Long useId) {
        List<Order> list = new ArrayList<>();
        return list;
    }

    @Override
    public int createOrder(Order order) {
        long orderId = IDGenerator.getInstance().next();

        // 检查商品
        List<OrderDetail> detailListList = order.getDetailListList();
        for (OrderDetail detail : detailListList) {
            long productId = detail.getProductId();
            long productNumber = detail.getProductNumber();
            Product product = productService.findProductById(productId);
            if (product == null) {
                throw new ServiceException(OrderExceptionEnum.product_no_exits.getCode(), OrderExceptionEnum.product_no_exits.getMsg());
            }
            if (product.getStatus() != ProductStatusEnum.on_line.getCode()) {
                throw new ServiceException(OrderExceptionEnum.product_no_sell.getCode(), OrderExceptionEnum.product_no_sell.getMsg());
            }
            if (productNumber > product.getStock()) {
                throw new ServiceException(OrderExceptionEnum.product_no_enough.getCode(), OrderExceptionEnum.product_no_enough.getMsg());
            }

            // TODO 扣库存
            long detailId = IDGenerator.getInstance().next();
            BigDecimal amount = product.getPrice().multiply(new BigDecimal(detail.getProductNumber()));
            detail.setId(detailId);
            detail.setOrderId(orderId);
            detail.setProductPrice(product.getPrice());
            detail.setAmount(amount);
        }

        // TODO 新增订单
        order.setId(orderId);
        return 0;
    }
}
