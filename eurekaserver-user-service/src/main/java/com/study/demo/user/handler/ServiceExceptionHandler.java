package com.study.demo.user.handler;

import com.study.demo.common.enums.CommonErrorEnum;
import com.study.demo.common.exception.ServiceException;
import com.study.demo.common.response.ApiRepsonseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Lon
 * @Date: 2020/4/24 11:22
 * @Description: 业务异常拦截器
 */
@Slf4j
@ControllerAdvice
public class ServiceExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiRepsonseResult exceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return new ApiRepsonseResult(CommonErrorEnum.PARAM_ERROR.getCode(), bindingResult.getFieldErrors()
                .get(0).getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ApiRepsonseResult exceptionHandler(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        return new ApiRepsonseResult(CommonErrorEnum.PARAM_ERROR.getCode(), bindingResult.getFieldErrors()
                .get(0).getDefaultMessage());
    }

    //全局业务异常捕捉处理
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public ApiRepsonseResult exceptionHandler(ServiceException se) {
        return new ApiRepsonseResult(se.getCode(), se.getMessage());
    }

    //全局异常捕捉处理
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ApiRepsonseResult errorHandler(Exception e) {
        log.error("error:{}", e.getMessage(), e);
        return ApiRepsonseResult.ofFail();
    }
}
