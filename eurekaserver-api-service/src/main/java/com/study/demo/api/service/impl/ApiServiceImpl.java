package com.study.demo.api.service.impl;

import com.study.demo.api.service.ApiService;
import com.study.demo.common.domain.Order;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/12 16:37
 * @Description: 开放接口基础服务实现类
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Override
    public List<Order> findUserAllOrderInfo(Long useId) {
        return null;
    }

    @Override
    public Order createOrder(Order order) {
        return null;
    }
}
