package com.study.demo.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.study.demo.order.service.OrderService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/4 15:36
 * @Description: 订单服务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public List<JSONObject> findUserAllOrderInfo(Long useId) {
        List<JSONObject> list = new ArrayList<>();

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", 1);
        jsonObject1.put("orderNo", 1);
        list.add(jsonObject1);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("id", 2);
        jsonObject2.put("orderNo", 2);
        list.add(jsonObject2);
        return list;
    }
}
