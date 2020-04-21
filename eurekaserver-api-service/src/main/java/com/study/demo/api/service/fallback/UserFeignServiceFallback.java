package com.study.demo.api.service.fallback;

import com.study.demo.api.service.UserFeignService;
import com.study.demo.common.domain.User;
import com.study.demo.common.vo.TokenVO;
import com.study.demo.common.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author: Lon
 * @Date: 2020/4/21 9:17
 * @Description: 调用user服务基础接口本地熔断处理类
 */
@Slf4j
@Service
public class UserFeignServiceFallback implements UserFeignService {


    @Override
    public User findUserById(Long userId) {
        log.error("远程调用[findUserById]失败, userId = {}", userId);
        User user = new User();
        user.setIsFeign(true);
        return user;
    }


    @Override
    public Integer reduceUserScore(Long id, BigDecimal score) {
        return null;
    }

    @Override
    public TokenVO login(UserVO userVO) {
        log.error("远程调用[login]失败, username = {}", userVO.getUsername());
        TokenVO tokenVO = new TokenVO();
        tokenVO.setIsFeign(true);
        return tokenVO;
    }
}
