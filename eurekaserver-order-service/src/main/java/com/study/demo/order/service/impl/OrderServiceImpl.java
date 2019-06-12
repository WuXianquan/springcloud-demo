package com.study.demo.order.service.impl;

import com.common.domain.Order;
import com.common.domain.OrderDetail;
import com.common.domain.Product;
import com.common.enums.OrderExceptionEnum;
import com.common.enums.OrderStatusEnum;
import com.common.enums.ProductStatusEnum;
import com.common.exception.ServiceException;
import com.common.util.IDGenerator;
import com.study.demo.order.repository.OrderRepository;
import com.study.demo.order.service.OrderDetailService;
import com.study.demo.order.service.OrderService;
import com.study.demo.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/4 15:36
 * @Description: 订单服务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public List<Order> findUserAllOrderInfo(Long useId) {
        return orderRepository.findUserAllOrderInfo(useId);
    }

    @Override
    public Order createOrder(Order order) {
        long orderId = IDGenerator.getInstance().next();

        // 检查商品
        BigDecimal totalAmount = new BigDecimal(0);
        List<OrderDetail> detailListList = order.getDetailListList();
        for (OrderDetail detail : detailListList) {
            long productId = detail.getProductId();
            long productNumber = detail.getProductNumber();
            Product product = productService.findProductById(productId);
            if (product == null) {
                throw new ServiceException(OrderExceptionEnum.product_no_exits.getCode(), OrderExceptionEnum.product_no_exits.getMsg());
            }
            if (product.getStatus().intValue() != ProductStatusEnum.on_line.getCode().intValue()) {
                throw new ServiceException(OrderExceptionEnum.product_no_sell.getCode(), OrderExceptionEnum.product_no_sell.getMsg());
            }

            // 预扣库存(订单完成之后预补回来)
            int ret = productService.reduceProductStock(productId, productNumber);
            if (ret != 1) {
                throw new ServiceException(OrderExceptionEnum.product_amount_error.getCode(), OrderExceptionEnum.product_amount_error.getMsg());
            }
            // 计算明细
            long detailId = IDGenerator.getInstance().next();
            BigDecimal amount = product.getPrice().multiply(new BigDecimal(detail.getProductNumber()));
            detail.setId(detailId);
            detail.setOrderId(orderId);
            detail.setProductPrice(product.getPrice());
            detail.setAmount(amount);
            totalAmount = totalAmount.add(amount);
        }
        // 保存订单明细
        orderDetailService.createOrderDetailList(detailListList);

        // 保存订单
        order.setId(orderId);
        order.setUserId(order.getUserId());
        order.setOrderAmount(totalAmount);
        order.setOrderStatus(OrderStatusEnum.NEW.getCode());
        order.setDetailListList(detailListList);
        orderRepository.save(order);

        // TODO 支付
        // TODO 实扣库存
        return order;
    }
}
