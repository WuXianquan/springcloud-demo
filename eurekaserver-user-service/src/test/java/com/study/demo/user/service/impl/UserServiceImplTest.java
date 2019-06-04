package com.study.demo.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.study.demo.user.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/4 17:03
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void findUserAllOrderInfo() {
        List<JSONObject> list = userService.findUserAllOrderInfo(Long.valueOf(1));
        Assert.assertNotSame(0, list.size());
    }
}