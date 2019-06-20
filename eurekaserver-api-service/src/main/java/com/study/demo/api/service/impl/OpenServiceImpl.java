package com.study.demo.api.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.study.demo.api.service.OpenService;
import com.study.demo.api.service.OrderService;
import com.study.demo.api.service.UserService;
import com.study.demo.common.domain.Order;
import com.study.demo.common.enums.OrderExceptionEnum;
import com.study.demo.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Lon
 * @Date: 2019/6/20 18:04
 * @Description: 聚合开放服务实现类
 */
@Service
public class OpenServiceImpl implements OpenService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Override
    @LcnTransaction
    public Order createOrder(Order order) {
        // 下单
        order = orderService.createOrder(order);
        if (order.getId() == null) {
            throw new ServiceException(OrderExceptionEnum.create_order_error.getCode(), OrderExceptionEnum.create_order_error.getMsg());
        }
        // 扣减用户积分
        int ret = userService.reduceUserScore(order.getUserId(), order.getOrderAmount());
        if (ret != 1) {
            throw new ServiceException(OrderExceptionEnum.reduce_user_socre_error.getCode(), OrderExceptionEnum.reduce_user_socre_error.getMsg());
        }
        return order;
    }
}
