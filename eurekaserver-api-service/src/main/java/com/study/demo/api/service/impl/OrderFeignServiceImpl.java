package com.study.demo.api.service.impl;

import com.study.demo.api.service.OrderFeignService;
import com.study.demo.common.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/12 16:37
 * @Description: 调用order服务基础接口实现类
 */
@Service
@Slf4j
public class OrderFeignServiceImpl implements OrderFeignService {

    @Override
    public List<Order> findUserAllOrderInfo(Long useId) {
        return null;
    }

    @Override
    public Order createOrder(Order order) {
        return null;
    }
}
