package com.study.demo.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.study.demo.order.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/4 15:58
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void findUserAllOrderInfo() {
        List<JSONObject> list = orderService.findUserAllOrderInfo(Long.valueOf(1));
        Assert.assertNotSame(0, list.size());
    }
}