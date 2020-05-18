package com.study.demo.user.service;

import com.study.demo.common.domain.User;
import com.study.demo.common.vo.TokenVO;
import com.study.demo.common.vo.UserVO;

import java.math.BigDecimal;

/**
 * @Author: Lon
 * @Date: 2019/6/4 15:51
 * @Description: 用户接口
 */
public interface UserService {

    /**
     * 用户注册
     * @param userVO 用户参数
     * @return
     */
    Long register(UserVO userVO);

    /**
     * 用户登录
     * @param userVO 用户参数
     * @return
     */
    TokenVO login(UserVO userVO);

    User findUserById(Long id) ;

    User findUserByUsername(String username);

    User updateUserInfo(User user);

    Integer addUserScore(Long id, BigDecimal score);

    Integer reduceUserScore(Long id, BigDecimal score);


}
