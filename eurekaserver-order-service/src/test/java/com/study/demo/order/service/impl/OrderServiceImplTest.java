package com.study.demo.order.service.impl;

import com.common.domain.Order;
import com.common.domain.OrderDetail;
import com.common.enums.OrderStatusEnum;
import com.study.demo.order.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/4 15:58
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceImplTest {

    private static final long userId = 123456789L;
    private static final long productId = 298614743086075904L;
    private static final long productId1 = 298660629273055232L;

    @Autowired
    private OrderService orderService;

    @Test
    public void findUserAllOrderInfo() {
        List<Order> orderList = orderService.findUserAllOrderInfo(userId);
        Assert.assertNotEquals(0, orderList.size());
    }

    @Test
    public void createOrder() {
        OrderDetail detail = new OrderDetail();
        detail.setProductId(productId);
        detail.setProductNumber(5);

        OrderDetail detail1 = new OrderDetail();
        detail1.setProductId(productId1);
        detail1.setProductNumber(3);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(detail);
        orderDetailList.add(detail1);
        Order order = new Order();
        order.setUserId(userId);
        order.setDetailListList(orderDetailList);
        order.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderService.createOrder(order);
        Assert.assertEquals(order.getId(), detail.getOrderId());
        Assert.assertEquals(order.getId(), detail1.getOrderId());
    }
}