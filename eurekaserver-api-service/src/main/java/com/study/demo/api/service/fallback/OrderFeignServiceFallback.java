package com.study.demo.api.service.fallback;

import com.study.demo.api.service.OrderFeignService;
import com.study.demo.common.domain.Order;
import com.study.demo.common.response.ApiRepsonseResult;
import com.study.demo.common.util.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: Lon
 * @Date: 2020/4/16 16:37
 * @Description: 调用order服务基础接口本地熔断处理类
 */
@Slf4j
@Service
public class OrderFeignServiceFallback implements OrderFeignService {

    @Override
    public ApiRepsonseResult findUserAllOrderInfo(Long useId) {
        log.error("远程调用[findUserAllOrderInfo]失败, userId = {}", useId);
        return ApiRepsonseResult.ofServiceFusesFail();
    }

    @Override
    public ApiRepsonseResult createOrder(Order order) {
        log.error("远程调用[createOrder]失败, userId = {}", order.getUserId());
        return ApiRepsonseResult.ofServiceFusesFail();
    }

    @Override
    public ApiRepsonseResult listProduct(PageHelper pageHelper) {
        log.error("远程调用[listProduct]失败");
        return ApiRepsonseResult.ofServiceFusesFail();
    }
}
