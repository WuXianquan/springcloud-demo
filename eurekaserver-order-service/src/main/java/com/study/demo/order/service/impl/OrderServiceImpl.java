package com.study.demo.order.service.impl;

import com.common.exception.ServiceException;
import com.common.util.IDGenerator;
import com.study.demo.order.domain.Order;
import com.study.demo.order.domain.OrderDetail;
import com.study.demo.order.domain.Product;
import com.study.demo.order.enums.OderStatusEnum;
import com.study.demo.order.enums.OrderExceptionEnum;
import com.study.demo.order.enums.ProductStatusEnum;
import com.study.demo.order.repository.OrderRepository;
import com.study.demo.order.service.OrderDetailService;
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
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public List<Order> findUserAllOrderInfo(Long useId) {
        List<Order> list = new ArrayList<>();
        return list;
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
            if (product.getStatus() != ProductStatusEnum.on_line.getCode()) {
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
            totalAmount.add(amount);
        }
        // 保存订单明细
        List<OrderDetail> list = orderDetailService.createOrderDetailList(detailListList);
        if (list == null) {
            throw new ServiceException("下单失败");
        }
        // 保存订单
        order.setId(orderId);
        order.setUsername(order.getUsername());
        order.setOrderAmount(totalAmount);
        order.setOrderStatus(OderStatusEnum.NEW.getCode());
        order.setDetailListList(detailListList);
        Order o = orderRepository.save(order);
        if (o == null) {
            throw new ServiceException("下单失败");
        }
        // TODO 支付
        // TODO 实扣库存
        return o;
    }
}
