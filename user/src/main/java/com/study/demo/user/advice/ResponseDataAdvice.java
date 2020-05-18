package com.study.demo.user.advice;

import com.alibaba.fastjson.JSON;
import com.study.demo.common.enums.CommonErrorEnum;
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

import java.util.LinkedHashMap;

/**
 * @Author: Lon
 * @Date: 2020/4/16 14:14
 * @Description: API接口返回数据前置处理类
 */
@Slf4j
@RestControllerAdvice
public class ResponseDataAdvice implements ResponseBodyAdvice<Object> {

    @Override
    @SuppressWarnings("all")
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
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
        // 请求400/404错误处理
        if (o instanceof LinkedHashMap) {
            int code = (int) ((LinkedHashMap) o).get("status");
            if (code == CommonErrorEnum.BUSINESS_ERROR.getCode().intValue()) {
                return new ApiRepsonseResult(CommonErrorEnum.BUSINESS_ERROR.getCode(), CommonErrorEnum.BUSINESS_ERROR.getMsg());
            }
            if (code == CommonErrorEnum.NOT_FOUND.getCode().intValue()) {
                return new ApiRepsonseResult(CommonErrorEnum.NOT_FOUND.getCode(), CommonErrorEnum.NOT_FOUND.getMsg());
            }
        }


        // o is null -> return response
        if (o == null) {
            return ApiRepsonseResult.ofSuccess();
        }
        // o is instanceof ConmmonResponse -> return o
        if (o instanceof ApiRepsonseResult) {
            return o;
        }
        // string 特殊处理
        if (o instanceof String) {
            return JSON.toJSONString(ApiRepsonseResult.ofSuccess(o));
        }
        return ApiRepsonseResult.ofSuccess(o);
    }
}