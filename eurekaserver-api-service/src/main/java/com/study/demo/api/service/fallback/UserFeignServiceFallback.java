package com.study.demo.api.service.fallback;

import com.study.demo.api.service.UserFeignService;
import com.study.demo.common.response.ApiRepsonseResult;
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
    public ApiRepsonseResult register(UserVO userVO) {
        log.error("远程调用[register]失败, username = {}, password = {}", userVO.getUsername(), userVO.getPassword());
        return ApiRepsonseResult.ofServiceFusesFail();
    }

    @Override
    public ApiRepsonseResult login(UserVO userVO) {
        log.error("远程调用[login]失败, username = {}, password = {}", userVO.getUsername(), userVO.getPassword());
        return ApiRepsonseResult.ofServiceFusesFail();
    }

    @Override
    public ApiRepsonseResult findUserById(Long userId) {
        log.error("远程调用[findUserById]失败, userId = {}", userId);
        return ApiRepsonseResult.ofServiceFusesFail();
    }

    @Override
    public Integer reduceUserScore(Long id, BigDecimal score) {
        return null;
    }
}
