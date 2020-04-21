package com.study.demo.api.advice;

import com.alibaba.fastjson.JSON;
import com.study.demo.api.annotation.IgnorReponseAdvice;
import com.study.demo.api.config.FilterConfig;
import com.study.demo.common.base.BaseDomain;
import com.study.demo.common.enums.CommonErrorCode;
import com.study.demo.common.response.ApiRepsonseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author: Lon
 * @Date: 2020/4/16 14:14
 * @Description: API接口返回数据前置处理类
 */
@Slf4j
@RestControllerAdvice
public class ResponseDataAdvice implements ResponseBodyAdvice<Object> {

    private FilterConfig filterConfig;

    public ResponseDataAdvice(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    @SuppressWarnings("all")
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return filter(methodParameter);
    }

    /**
     * 对API接口返回的数据做统一格式处理，封装返回ApiRepsonseResult
     *
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Nullable
    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        // o is null -> return response
        if (o == null) {
            return ApiRepsonseResult.ofSuccess();
        }
        // o is instanceof ConmmonResponse -> return o
        if (o instanceof ApiRepsonseResult) {
            return o;
        }
        // hystrix 降级返回特殊处理
        if (o instanceof BaseDomain) {
            if (((BaseDomain) o).getIsFeign() != null && ((BaseDomain) o).getIsFeign()) {
                return new ApiRepsonseResult(CommonErrorCode.SERVICE_DOWN_ERROR.getCode(), CommonErrorCode.SERVICE_DOWN_ERROR.getMsg());
            }
        }

        // string 特殊处理
        if (o instanceof String) {
            return JSON.toJSONString(ApiRepsonseResult.ofSuccess(o));
        }
        return ApiRepsonseResult.ofSuccess(o);
    }

    /**
     * 判断是否需要对返回的数据做统一处理
     *
     * @param methodParameter
     * @return
     */
    private Boolean filter(MethodParameter methodParameter) {
        Class<?> declaringClass = methodParameter.getDeclaringClass();
        // 检查过滤包路径
        long count = filterConfig.getAdviceFilterPackage().stream()
                .filter(l -> declaringClass.getName().contains(l)).count();
        if (count > 0) {
            return false;
        }
        // 检查<类>过滤列表
        if (filterConfig.getAdviceFilterClass().contains(declaringClass.getName())) {
            return false;
        }
        // 检查注解是否存在
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnorReponseAdvice.class)) {
            return false;
        }
        if (methodParameter.getMethod().isAnnotationPresent(IgnorReponseAdvice.class)) {
            return false;
        }
        return true;
    }
}