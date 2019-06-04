package com.study.demo.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/4 17:17
 * @Description:
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/orderList/{userId}")
    public List<JSONObject> orderList(@PathVariable Long userId) {
        return userService.findUserAllOrderInfo(userId);
    }
}
