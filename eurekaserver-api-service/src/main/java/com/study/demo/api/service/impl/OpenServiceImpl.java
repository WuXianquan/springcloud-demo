package com.study.demo.api.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.study.demo.api.service.OpenService;
import com.study.demo.api.service.OrderFeignService;
import com.study.demo.api.service.UserFeignService;
import com.study.demo.api.util.RedisUtil;
import com.study.demo.common.consts.TokenConst;
import com.study.demo.common.domain.Order;
import com.study.demo.common.domain.User;
import com.study.demo.common.dto.UserDTO;
import com.study.demo.common.enums.ResponseEnum;
import com.study.demo.common.response.ApiRepsonseResult;
import com.study.demo.common.util.MapUtil;
import com.study.demo.common.vo.TokenVO;
import com.study.demo.common.enums.OrderExceptionEnum;
import com.study.demo.common.exception.ServiceException;
import com.study.demo.common.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Lon
 * @Date: 2019/6/20 18:04
 * @Description: 聚合开放服务实现类
 */
@Slf4j
@Service
public class OpenServiceImpl implements OpenService {

    @Autowired
    private OrderFeignService orderFeignService;

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public TokenVO register(UserVO userVO) {
        ApiRepsonseResult result = userFeignService.register(userVO);
        if (result.getCode().intValue() != ResponseEnum.SUCCESS.getCode().intValue()) {
            throw new ServiceException(result);
        }

        // token做redis缓存处理
        TokenVO tokenVO = MapUtil.convertToBean(result.getBody(), TokenVO.class);
        redisUtil.setForTimeS(TokenConst.TOKEN_REDISKEY_PRE_USERNAME + userVO.getUsername(), tokenVO.getToken(),
                tokenVO.getExpireTime() - 3);
        return tokenVO;
    }

    @Override
    public TokenVO login(UserVO userVO) {
        // 从redis读取用户信息
        String reKey = TokenConst.TOKEN_REDISKEY_PRE_USERNAME + userVO.getUsername();
        String reToken = redisUtil.get(reKey);
        if (StringUtils.isNotEmpty(reToken)) {
            Long expireTime = redisUtil.getExpire(reKey, TimeUnit.SECONDS);
            if (expireTime != null && expireTime.longValue() > 0) {
                TokenVO tokenVO = new TokenVO();
                tokenVO.setToken(reToken);
                tokenVO.setExpireTime(expireTime);
                return tokenVO;
            }
        }

        ApiRepsonseResult result = userFeignService.login(userVO);
        if (result.getCode().intValue() != ResponseEnum.SUCCESS.getCode().intValue()) {
            throw new ServiceException(result);
        }

        // token做redis缓存处理
        TokenVO tokenVO = MapUtil.convertToBean(result.getBody(), TokenVO.class);
        redisUtil.setForTimeS(reKey, tokenVO.getToken(), tokenVO.getExpireTime() - 3);
        return tokenVO;
    }

    @Override
    @LcnTransaction
    public Order createOrder(Order order) {
        // 下单
        order = orderFeignService.createOrder(order);
        if (order.getId() == null) {
            throw new ServiceException(OrderExceptionEnum.create_order_error.getCode(),
                    OrderExceptionEnum.create_order_error.getMsg());
        }
        // 扣减用户积分
        int ret = userFeignService.reduceUserScore(order.getUserId(), order.getOrderAmount());
        if (ret != 1) {
            throw new ServiceException(OrderExceptionEnum.reduce_user_socre_error.getCode(),
                    OrderExceptionEnum.reduce_user_socre_error.getMsg());
        }
        return order;
    }

    @Override
    public UserDTO findUserById(Long userId) {
        ApiRepsonseResult result = userFeignService.findUserById(userId);
        if (result.getCode().intValue() != ResponseEnum.SUCCESS.getCode().intValue()) {
            throw new ServiceException(result);
        }
        User user = MapUtil.convertToBean(result.getBody(), User.class);
        return new UserDTO(user);
    }
}
