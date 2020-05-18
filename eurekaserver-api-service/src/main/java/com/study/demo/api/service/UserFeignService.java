package com.study.demo.api.service;

import com.study.demo.api.service.fallback.UserFeignServiceFallback;
import com.study.demo.common.response.ApiRepsonseResult;
import com.study.demo.common.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @Author: Lon
 * @Date: 2019/6/19 15:04
 * @Description: 调用user服务基础接口
 */
//@FeignClient(name = "user-service", fallback = UserFeignServiceFallback.class)
@FeignClient(name = "user-service", url = "127.0.0.1:8082", fallback = UserFeignServiceFallback.class)
public interface UserFeignService {

    @PostMapping(value = "user/register")
    ApiRepsonseResult register(UserVO userVO);

    @PostMapping(value = "user/login")
    ApiRepsonseResult login(UserVO userVO);

    @GetMapping(value = "user/userInfo/id/{id}")
    ApiRepsonseResult findUserById(@PathVariable("id") Long userId);

    @GetMapping(value = "user/userInfo/username/{username}")
    ApiRepsonseResult findUserByUsername(@PathVariable("username") String username);

    @PostMapping(value = "user/reduceUserScore")
    Integer reduceUserScore(@RequestParam("id") Long id, @RequestParam("score") BigDecimal score);
}
