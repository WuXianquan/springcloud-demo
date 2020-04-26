package com.study.demo.order.handler;

import com.study.demo.common.exception.ServiceException;
import com.study.demo.common.response.ApiRepsonseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Lon
 * @Date: 2020/4/24 11:22
 * @Description: 业务异常拦截器
 */
@ControllerAdvice
public class ServiceExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public ApiRepsonseResult exceptionHandler(ServiceException se) {
        return new ApiRepsonseResult(se.getCode(), se.getMessage());
    }
}
