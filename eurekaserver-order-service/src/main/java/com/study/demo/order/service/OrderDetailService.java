package com.study.demo.order.service;

import com.common.domain.OrderDetail;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/11 19:42
 * @Description: 订单明细服务接口类
 */
public interface OrderDetailService {

    OrderDetail findOrderDetailById(Long id);

    List<OrderDetail> findOrderDetailListByOrderId(Long orderId);

    OrderDetail createOrderDetail(OrderDetail orderDetail);

    List<OrderDetail> createOrderDetailList(List<OrderDetail> orderDetailList);
}
