package com.study.demo.api.service;

import com.study.demo.api.service.fallback.UserFeignServiceFallback;
import com.study.demo.common.domain.User;
import com.study.demo.common.vo.TokenVO;
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
@FeignClient(name = "user-service", fallback = UserFeignServiceFallback.class)
public interface UserFeignService {

    @GetMapping(value = "user/userInfo/{id}")
    User findUserById(@PathVariable("id") Long userId);

    @PostMapping(value = "user/reduceUserScore")
    Integer reduceUserScore(@RequestParam("id") Long id, @RequestParam("score") BigDecimal score);

    @PostMapping(value = "user/login")
    TokenVO login(UserVO userVO);
}
