package com.study.demo.api.service.impl;

import com.study.demo.api.service.UserFeignService;
import com.study.demo.common.domain.User;
import com.study.demo.common.vo.TokenVO;
import com.study.demo.common.vo.UserVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author: Lon
 * @Date: 2019/6/19 15:10
 * @Description: 调用user服务基础接口实现类
 */
@Service
public class UserFeignServiceImpl implements UserFeignService {

    @Override
    public User findUserById(Long userId) {
        return null;
    }

    @Override
    public Integer reduceUserScore(Long id, BigDecimal score) {
        return null;
    }

    @Override
    public TokenVO login(UserVO userVO) {
        return null;
    }
}
