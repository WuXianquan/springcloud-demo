package com.study.demo.api.service;

import com.study.demo.api.service.fallback.OrderFeignServiceFallback;
import com.study.demo.common.domain.Order;
import com.study.demo.common.response.ApiRepsonseResult;
import com.study.demo.common.util.PageHelper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Lon
 * @Date: 2019/6/12 16:19
 * @Description: 调用order服务基础接口
 */
//@FeignClient(name = "order-service", fallback = OrderFeignServiceFallback.class)
@FeignClient(name = "order-service", url = "127.0.0.1:8081", fallback = OrderFeignServiceFallback.class)

public interface OrderFeignService {

    @GetMapping(value = "/order/userOrder/{userId}")
    ApiRepsonseResult findUserAllOrderInfo(@PathVariable("userId") Long useId);

    @PostMapping(value = "/order/createOrder")
    ApiRepsonseResult createOrder(@RequestBody Order order);

    @GetMapping(value = "/product/list")
    ApiRepsonseResult listProduct(@RequestBody PageHelper pageHelper);
}
