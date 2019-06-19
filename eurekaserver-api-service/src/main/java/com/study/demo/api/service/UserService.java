package com.study.demo.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @Author: Lon
 * @Date: 2019/6/19 15:04
 * @Description: 调用user服务基础接口
 */
@FeignClient(name = "user-service")
public interface UserService {

    @PostMapping(value = "user/reduceUserScore")
    Integer reduceUserScore(@RequestParam("id") Long id, @RequestParam("score") BigDecimal score);
}
