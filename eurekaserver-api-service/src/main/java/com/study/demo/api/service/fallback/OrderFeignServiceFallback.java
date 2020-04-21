package com.study.demo.api.service.fallback;

import com.study.demo.api.service.OrderFeignService;
import com.study.demo.common.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2020/4/16 16:37
 * @Description: 调用order服务基础接口本地熔断处理类
 */
@Slf4j
@Service
public class OrderFeignServiceFallback implements OrderFeignService {

    @Override
    public List<Order> findUserAllOrderInfo(Long useId) {
        log.error("远程调用[findUserAllOrderInfo]失败, userId = {}", useId);
        return null;
    }

    @Override
    public Order createOrder(Order order) {
        log.error("远程调用[createOrder]失败, userId = {}", order.getUserId());
        return new Order();
    }
}
