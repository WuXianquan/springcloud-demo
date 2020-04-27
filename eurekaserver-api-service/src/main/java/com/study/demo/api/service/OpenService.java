package com.study.demo.api.service;

import com.study.demo.common.domain.Order;
import com.study.demo.common.dto.UserDTO;
import com.study.demo.common.util.PageHelper;
import com.study.demo.common.vo.TokenVO;
import com.study.demo.common.vo.UserVO;

/**
 * @Author: Lon
 * @Date: 2019/6/20 18:03
 * @Description: 聚合开放接口类
 */
public interface OpenService {

    TokenVO register(UserVO userVO);

    TokenVO login(UserVO userVO);

    Order createOrder(Order order);

    UserDTO findUserById(Long userId);

    PageHelper listProduct(PageHelper pageHelper);

    PageHelper listOnLineProduct(PageHelper pageHelper);
}
