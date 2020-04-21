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
import com.study.demo.common.domain.User;
import com.study.demo.common.enums.CommonErrorCode;
import com.study.demo.common.enums.UserExceptionEnum;
import com.study.demo.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

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
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
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
                    throw new ServiceException(CommonErrorCode.NO_LOGIN_ERROR.getCode(), CommonErrorCode.NO_LOGIN_ERROR.getMsg());
                }

                // 获取token中的 userId
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new ServiceException(CommonErrorCode.ILLEGAL_REQUEST.getCode(), CommonErrorCode.ILLEGAL_REQUEST.getMsg());
                }

                // redis校验token
                String rt = redisUtil.get("user:token" + userId);
                if (rt != null) {
                    return true;
                }

                User user = userFeignService.findUserById(Long.valueOf(userId));
                if (user == null) {
                    throw new ServiceException(UserExceptionEnum.USER_NO_EXITS.getCode(), UserExceptionEnum.USER_NO_EXITS.getMsg());
                }
                // 判断是否服务降级
                if (user.getIsFeign() != null && user.getIsFeign()) {
                    throw new ServiceException(CommonErrorCode.SERVICE_DOWN_ERROR.getCode(), CommonErrorCode.SERVICE_DOWN_ERROR.getMsg());
                }

                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    log.error("JWT token校验不通过, token = {}", token);
                    throw new ServiceException(CommonErrorCode.ILLEGAL_REQUEST.getCode(), CommonErrorCode.ILLEGAL_REQUEST.getMsg());
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) {
    }
}
