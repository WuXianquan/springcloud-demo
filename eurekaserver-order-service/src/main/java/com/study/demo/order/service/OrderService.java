package com.study.demo.order.service;

import com.study.demo.order.domain.Order;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/4 15:22
 * @Description: 订单服务接口
 */
public interface OrderService {

    /**
     * 获取用户所有订单
     * @param userId 用户ID
     * @return
     */
    List<Order> findUserAllOrderInfo(Long userId);

    /**
     * 下单
     * @param order
     * @return
     */
    Order createOrder(Order order);
}
