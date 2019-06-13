package com.study.demo.order.controller;

import com.study.demo.common.domain.Order;
import com.study.demo.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/userOrder/{userId}", method = RequestMethod.GET)
    public List<Order> userOrder(@PathVariable("userId") Long userId) {
        return orderService.findUserAllOrderInfo(userId);
    }
}
