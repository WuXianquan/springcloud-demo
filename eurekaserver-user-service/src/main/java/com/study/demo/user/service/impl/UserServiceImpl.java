package com.study.demo.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.study.demo.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/4 15:55
 * @Description: 用户接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<JSONObject> findUserAllOrderInfo(Long useId) {
        return null;
    }
}
