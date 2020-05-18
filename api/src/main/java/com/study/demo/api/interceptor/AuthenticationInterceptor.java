package com.study.demo.api.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.study.demo.api.annotation.PassToken;
import com.study.demo.api.annotation.UserLoginToken;
import com.study.demo.api.service.UserFeignService;
import com.study.demo.api.util.RedisUtil;
import com.study.demo.common.consts.TokenConst;
import com.study.demo.common.domain.User;
import com.study.demo.common.enums.CommonErrorEnum;
import com.study.demo.common.enums.ResponseEnum;
import com.study.demo.common.enums.UserExceptionEnum;
import com.study.demo.common.exception.ServiceException;
import com.study.demo.common.response.ApiRepsonseResult;
import com.study.demo.common.util.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Lon
 * @Date: 2020/4/20 16:23
 * @Description: JWT token校验拦截器
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {
        String token = httpServletRequest.getHeader(TokenConst.TOKEN_HEAD);// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        // 检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new ServiceException(CommonErrorEnum.NO_LOGIN_ERROR.getCode(), CommonErrorEnum.NO_LOGIN_ERROR.getMsg());
                }

                // 获取token中的 userId
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new ServiceException(CommonErrorEnum.ILLEGAL_REQUEST.getCode(), CommonErrorEnum.ILLEGAL_REQUEST.getMsg());
                }

                // redis校验token
                String redisKey = TokenConst.TOKEN_REDISKEY_PRE_ID + userId;
                String rt = redisUtil.get(redisKey);
                if (rt != null) {
                    return true;
                }

                ApiRepsonseResult result = userFeignService.findUserById(Long.valueOf(userId));
                if (result.getCode().intValue() != ResponseEnum.SUCCESS.getCode().intValue()) {
                    throw new ServiceException(result.getCode(), result.getMsg());
                }

                User user = MapUtil.convertToBean(result.getBody(), User.class);
                if (user == null) {
                    throw new ServiceException(UserExceptionEnum.USER_NO_EXITS.getCode(), UserExceptionEnum.USER_NO_EXITS.getMsg());
                }

                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    log.error("JWT token校验不通过, token = {}", token);
                    throw new ServiceException(CommonErrorEnum.ILLEGAL_REQUEST.getCode(), CommonErrorEnum.ILLEGAL_REQUEST.getMsg());
                }
                Long expireTime = redisUtil.getExpire(TokenConst.TOKEN_REDISKEY_PRE_USERNAME + user.getUsername(), TimeUnit.SECONDS);
                if (expireTime != null && expireTime > 0) {
                    redisUtil.setForTimeS(redisKey, token, expireTime - 1);
                }
                return true;
            }
        }
        return true;
    }
}
