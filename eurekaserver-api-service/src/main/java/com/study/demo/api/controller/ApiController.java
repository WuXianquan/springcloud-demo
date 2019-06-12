package com.study.demo.api.controller;

import com.common.domain.Order;
import com.study.demo.api.service.impl.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/12 16:32
 * @Description:
 */
@RestController
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "/orderList/{userId}", method = RequestMethod.GET)
    public List<Order> index(@PathVariable Long userId) {
        return apiService.findUserAllOrderInfo(userId);
    }
}
