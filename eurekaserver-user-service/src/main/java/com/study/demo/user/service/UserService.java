package com.study.demo.user.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/4 15:51
 * @Description: 用户接口
 */
@FeignClient(name = "order-service")
public interface UserService {

    @RequestMapping(value = "/findUserAllOrderInfo/{userId}", method = RequestMethod.GET)
    List<JSONObject> findUserAllOrderInfo(@PathVariable("userId") Long useId);
}
