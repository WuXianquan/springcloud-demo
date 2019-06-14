package com.study.demo.order.controller;

import com.study.demo.common.domain.Order;
import com.study.demo.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/4 16:43
 * @Description:
 */
@RestController
@RequestMapping(value = "order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/userOrder/{userId}")
    public List<Order> userOrder(@PathVariable("userId") Long userId) {
        return orderService.findUserAllOrderInfo(userId);
    }

    @PostMapping(value = "/createOrder")
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }
}
