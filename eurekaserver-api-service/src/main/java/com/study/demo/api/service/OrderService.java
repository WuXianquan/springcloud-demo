package com.study.demo.api.service;

import com.study.demo.common.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/12 16:19
 * @Description: 调用order服务基础接口
 */
@FeignClient(name = "order-service")
public interface OrderService {

    @GetMapping(value = "order/userOrder/{userId}")
    List<Order> findUserAllOrderInfo(@PathVariable("userId") Long useId);

    @PostMapping(value = "order/createOrder")
    Order createOrder(@RequestBody Order order);
}
