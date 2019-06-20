package com.study.demo.api.service;

import com.study.demo.common.domain.Order;

/**
 * @Author: Lon
 * @Date: 2019/6/20 18:03
 * @Description: 聚合开放接口类
 */
public interface OpenService {

    Order createOrder(Order order);
}
