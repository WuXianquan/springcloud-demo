package com.study.demo.user.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.study.demo.common.domain.User;
import com.study.demo.common.vo.TokenVO;
import com.study.demo.common.enums.ResponseEnum;
import com.study.demo.common.enums.UserExceptionEnum;
import com.study.demo.common.enums.UserStatusEnum;
import com.study.demo.common.exception.ServiceException;
import com.study.demo.common.util.MD5;
import com.study.demo.common.vo.UserVO;
import com.study.demo.user.repository.UserRepository;
import com.study.demo.user.service.UserService;
import com.study.demo.user.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @Author: Lon
 * @Date: 2019/6/4 15:55
 * @Description: 用户接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new ServiceException(UserExceptionEnum.USER_NO_EXITS.getCode(),UserExceptionEnum.USER_NO_EXITS.getMsg());
        }
        return user.get();
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new ServiceException(UserExceptionEnum.USER_NO_EXITS.getCode(),UserExceptionEnum.USER_NO_EXITS.getMsg());
        }
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User updateUserInfo(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public Integer addUserScore(Long id, BigDecimal score) {
        Optional<User> user = userRepository.findById(id);
        if (user == null) {
            throw new ServiceException(UserExceptionEnum.USER_NO_EXITS.getCode(), UserExceptionEnum.USER_NO_EXITS.getMsg());
        }
        int status = user.get().getStatus();
        if (status == UserStatusEnum.DELETE.getCode().intValue()) {
            throw new ServiceException(UserExceptionEnum.USER_IS_DELETE.getCode(), UserExceptionEnum.USER_IS_DELETE.getMsg());
        }
        if (status == UserStatusEnum.frozen.getCode().intValue()) {
            throw new ServiceException(UserExceptionEnum.USER_IS_FROZEN.getCode(), UserExceptionEnum.USER_IS_FROZEN.getMsg());
        }
        return userRepository.addUserScore(id, score);
    }

    @Transactional
    @LcnTransaction
    @Override
    public Integer reduceUserScore(Long id, BigDecimal score) {
        Optional<User> user = userRepository.findById(id);
        if (user == null) {
            throw new ServiceException(UserExceptionEnum.USER_NO_EXITS.getCode(), UserExceptionEnum.USER_NO_EXITS.getMsg());
        }
        int status = user.get().getStatus();
        if (status == UserStatusEnum.DELETE.getCode().intValue()) {
            throw new ServiceException(UserExceptionEnum.USER_IS_DELETE.getCode(), UserExceptionEnum.USER_IS_DELETE.getMsg());
        }
        if (status == UserStatusEnum.frozen.getCode().intValue()) {
            throw new ServiceException(UserExceptionEnum.USER_IS_FROZEN.getCode(), UserExceptionEnum.USER_IS_FROZEN.getMsg());
        }
        return userRepository.reduceUserScore(id, score);
    }

    @Override
    public TokenVO login(UserVO userVO) {
        TokenVO tokenVO = new TokenVO();
        User user = findUserByUsername(userVO.getUsername());
        if (user == null) {
            tokenVO.setCode(UserExceptionEnum.USER_NO_EXITS.getCode());
            tokenVO.setMsg(UserExceptionEnum.USER_NO_EXITS.getMsg());
            return tokenVO;
        }

        if (!MD5.getMD5(userVO.getPassword()).equals(user.getPassword())) {
            tokenVO.setCode(UserExceptionEnum.PASSWORD_ERROR.getCode());
            tokenVO.setMsg(UserExceptionEnum.PASSWORD_ERROR.getMsg());
            return tokenVO;
        }

        // 从redis读取用户token
        String rt = redisUtil.get("user:token:" + user.getId());
        if (rt != null) {
            tokenVO.setCode(ResponseEnum.SUCCESS.getCode());
            tokenVO.setMsg(ResponseEnum.SUCCESS.getMsg());
            tokenVO.setToken(rt);
            return tokenVO;
        }

        // 生成token
        String token = JWT.create().withAudience(String.valueOf(user.getId())).sign(Algorithm.HMAC256(user.getPassword()));
        tokenVO.setCode(ResponseEnum.SUCCESS.getCode());
        tokenVO.setMsg(ResponseEnum.SUCCESS.getMsg());
        tokenVO.setToken(token);

        // 存入redis
        redisUtil.setForTimeM("user:token:" + user.getId(), token, 60);
        return tokenVO;
    }
}
