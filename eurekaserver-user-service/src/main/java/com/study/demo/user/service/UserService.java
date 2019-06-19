package com.study.demo.user.service;

import com.study.demo.common.domain.User;

import java.math.BigDecimal;

/**
 * @Author: Lon
 * @Date: 2019/6/4 15:51
 * @Description: 用户接口
 */
public interface UserService {

    User findUserById(Long id);

    User findUserByUsername(String username);

    User updateUserInfo(User user);

    Integer addUserScore(Long id, BigDecimal score);

    Integer reduceUserScore(Long id, BigDecimal score);
}
