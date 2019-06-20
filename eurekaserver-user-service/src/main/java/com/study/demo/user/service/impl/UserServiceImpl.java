package com.study.demo.user.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.study.demo.common.domain.User;
import com.study.demo.common.enums.UserExceptionEnum;
import com.study.demo.common.enums.UserStatusEnum;
import com.study.demo.common.exception.ServiceException;
import com.study.demo.user.repository.UserRepository;
import com.study.demo.user.service.UserService;
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

    @Override
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return userRepository.findById(id).isPresent() ? user.get() : null;
    }

    @Override
    public User findUserByUsername(String username) {
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
            throw new ServiceException(UserExceptionEnum.user_no_exits.getCode(), UserExceptionEnum.user_no_exits.getMsg());
        }
        int status = user.get().getStatus();
        if (status == UserStatusEnum.DELETE.getCode().intValue()) {
            throw new ServiceException(UserExceptionEnum.user_is_delete.getCode(), UserExceptionEnum.user_is_delete.getMsg());
        }
        if (status == UserStatusEnum.frozen.getCode().intValue()) {
            throw new ServiceException(UserExceptionEnum.user_is_frozen.getCode(), UserExceptionEnum.user_is_frozen.getMsg());
        }
        return userRepository.addUserScore(id, score);
    }

    @Transactional
    @LcnTransaction
    @Override
    public Integer reduceUserScore(Long id, BigDecimal score) {
        Optional<User> user = userRepository.findById(id);
        if (user == null) {
            throw new ServiceException(UserExceptionEnum.user_no_exits.getCode(), UserExceptionEnum.user_no_exits.getMsg());
        }
        int status = user.get().getStatus();
        if (status == UserStatusEnum.DELETE.getCode().intValue()) {
            throw new ServiceException(UserExceptionEnum.user_is_delete.getCode(), UserExceptionEnum.user_is_delete.getMsg());
        }
        if (status == UserStatusEnum.frozen.getCode().intValue()) {
            throw new ServiceException(UserExceptionEnum.user_is_frozen.getCode(), UserExceptionEnum.user_is_frozen.getMsg());
        }
        return userRepository.reduceUserScore(id, score);
    }
}
