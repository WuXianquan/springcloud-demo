package com.study.demo.api.controller;

import com.study.demo.api.service.OpenService;
import com.study.demo.api.service.UserService;
import com.study.demo.common.domain.Order;
import com.study.demo.api.service.OrderService;
import com.study.demo.common.domain.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/12 16:32
 * @Description:
 */
@RestController
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OpenService openService;

    @GetMapping(value = "/userOrder/{userId}")
    public List<Order> userOrder(@PathVariable("userId") Long userId) {
        return orderService.findUserAllOrderInfo(userId);
    }

    @PostMapping(value = "/createOrder")
    public Order createOrder() {
        // 模拟测试
        Order order = new Order();
        OrderDetail detail = new OrderDetail();
        detail.setProductId(298614743086075904L);
        detail.setProductNumber(1);

        OrderDetail detail1 = new OrderDetail();
        detail1.setProductId(298660629273055232L);
        detail1.setProductNumber(1);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(detail);
        orderDetailList.add(detail1);

        order.setUserId(123456L);
        order.setDetailListList(orderDetailList);
        return openService.createOrder(order);
    }
}
