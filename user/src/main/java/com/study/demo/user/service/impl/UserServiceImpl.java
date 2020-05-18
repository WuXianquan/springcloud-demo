package com.study.demo.user.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.study.demo.common.consts.TokenConst;
import com.study.demo.common.domain.User;
import com.study.demo.common.enums.CommonErrorEnum;
import com.study.demo.common.util.IDGenerator;
import com.study.demo.common.vo.TokenVO;
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
import java.sql.Timestamp;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
    public Long register(UserVO userVO) {
        if (!userVO.getPassword().equals(userVO.getCheckPassword())) {
            throw new ServiceException(CommonErrorEnum.PARAM_ERROR.getCode(), CommonErrorEnum.PARAM_ERROR.getMsg());
        }

        // 校验用户合法性
        String key = TokenConst.TOKEN_REDISKEY_PRE_USERNAME + userVO.getUsername();
        String rt = redisUtil.get(key);
        if (rt != null) {
            throw new ServiceException(UserExceptionEnum.USERNAME_IS_USED.getCode(), UserExceptionEnum.USERNAME_IS_USED.getMsg());
        }

        User user = userRepository.findUserByUsername(userVO.getUsername());
        if (user != null) {
            throw new ServiceException(UserExceptionEnum.USERNAME_IS_USED.getCode(), UserExceptionEnum.USERNAME_IS_USED.getMsg());
        }

        User newUser = new User();
        newUser.setId(IDGenerator.getInstance().next());
        newUser.setUsername(userVO.getUsername());
        newUser.setPassword(MD5.getMD5(userVO.getPassword()));
        newUser.setScore(BigDecimal.ZERO);
        newUser.setStatus(UserStatusEnum.NORMAL.getCode());
        newUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
        User u = userRepository.saveAndFlush(newUser);
        return u.getId();
    }

    @Override
    public TokenVO login(UserVO userVO) {
        // 从redis读取用户token
        String key = TokenConst.TOKEN_REDISKEY_PRE_USERNAME + userVO.getUsername();
        String rt = redisUtil.get(key);
        if (rt != null) {
            Long expireTime = redisUtil.getExpire(key, TimeUnit.SECONDS);
            if (expireTime > 0) {
                // 刷新token时长
                TokenVO tokenVO = new TokenVO();
                tokenVO.setToken(rt);
                tokenVO.setExpireTime(TokenConst.TOKEN_REDISKEY_EXPIRETIME);
                redisUtil.setForTimeS(key, rt, TokenConst.TOKEN_REDISKEY_EXPIRETIME);
                return tokenVO;
            }
        }

        // 校验用户合法性
        User user = findUserByUsername(userVO.getUsername());
        if (user == null) {
            throw new ServiceException(UserExceptionEnum.USER_NO_EXITS.getCode(), UserExceptionEnum.USER_NO_EXITS.getMsg());
        }
        if (!MD5.getMD5(userVO.getPassword()).equals(user.getPassword())) {
            throw new ServiceException(UserExceptionEnum.PASSWORD_ERROR.getCode(), UserExceptionEnum.PASSWORD_ERROR.getMsg());
        }

        // 生成token
        String token = JWT.create().withAudience(String.valueOf(user.getId())).sign(Algorithm.HMAC256(user.getPassword()));
        TokenVO tokenVO = new TokenVO();
        tokenVO.setToken(token);
        tokenVO.setExpireTime(TokenConst.TOKEN_REDISKEY_EXPIRETIME);

        // 存入redis
        redisUtil.setForTimeS(key, token, TokenConst.TOKEN_REDISKEY_EXPIRETIME);
        return tokenVO;
    }


    @Override
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new ServiceException(UserExceptionEnum.USER_NO_EXITS.getCode(), UserExceptionEnum.USER_NO_EXITS.getMsg());
        }
        return user.get();
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new ServiceException(UserExceptionEnum.USER_NO_EXITS.getCode(), UserExceptionEnum.USER_NO_EXITS.getMsg());
        }
        return user;
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
}
