package com.study.demo.order.service.impl;

import com.study.demo.order.domain.OrderDetail;
import com.study.demo.order.repository.OrderDetailRepository;
import com.study.demo.order.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/11 19:50
 * @Description: 订单明细服务实现类
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail findOrderDetailById(Long id) {
        return orderDetailRepository.getOne(id);
    }

    @Override
    public List<OrderDetail> findOrderDetailListByOrderId(Long orderId) {
        return null;
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> createOrderDetailList(List<OrderDetail> orderDetailList) {
        return orderDetailRepository.saveAll(orderDetailList);
    }
}
