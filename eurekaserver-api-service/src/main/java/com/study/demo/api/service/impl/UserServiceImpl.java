package com.study.demo.api.service.impl;

import com.study.demo.api.service.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author: Lon
 * @Date: 2019/6/19 15:10
 * @Description: 调用user服务基础接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public Integer reduceUserScore(Long id, BigDecimal score) {
        return null;
    }
}
