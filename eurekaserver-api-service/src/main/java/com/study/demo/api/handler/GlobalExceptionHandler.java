package com.study.demo.api.handler;

import com.netflix.client.ClientException;
import com.study.demo.common.enums.CommonErrorCode;
import com.study.demo.common.exception.ServiceException;
import com.study.demo.common.response.ApiRepsonseResult;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @Author: Lon
 * @Date: 2020/4/16 14:26
 * @Description: 全局API接口异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * NoHandlerFoundException 404 异常处理
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiRepsonseResult handlerNoHandlerFoundException(NoHandlerFoundException exception) {
        outPutErrorWarn(NoHandlerFoundException.class, CommonErrorCode.NOT_FOUND, exception);
        return new ApiRepsonseResult(CommonErrorCode.NOT_FOUND.getCode(), CommonErrorCode.NOT_FOUND.getMsg());
    }

    /**
     * HttpRequestMethodNotSupportedException 405 异常处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiRepsonseResult handlerHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception) {
        outPutErrorWarn(HttpRequestMethodNotSupportedException.class,
                CommonErrorCode.METHOD_NOT_ALLOWED, exception);
        return new ApiRepsonseResult(CommonErrorCode.METHOD_NOT_ALLOWED.getCode(), CommonErrorCode.METHOD_NOT_ALLOWED.getMsg());
    }

    /**
     * HttpMediaTypeNotSupportedException 415 异常处理
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ApiRepsonseResult handlerHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception) {
        outPutErrorWarn(HttpMediaTypeNotSupportedException.class,
                CommonErrorCode.UNSUPPORTED_MEDIA_TYPE, exception);
        return new ApiRepsonseResult(CommonErrorCode.UNSUPPORTED_MEDIA_TYPE.getCode(), CommonErrorCode.UNSUPPORTED_MEDIA_TYPE.getMsg());
    }

    /**
     * Exception 类捕获 500 异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public ApiRepsonseResult handlerException(Exception e) {
        return ifDepthExceptionType(e);
    }

    /**
     * 二次深度检查错误类型
     */
    private ApiRepsonseResult ifDepthExceptionType(Throwable throwable) {
        Throwable cause = throwable.getCause();
        if (cause instanceof ClientException) {
            return handlerClientException((ClientException) cause);
        }
        if (cause instanceof FeignException) {
            return handlerFeignException((FeignException) cause);
        }
        outPutError(Exception.class, CommonErrorCode.EXCEPTION, throwable);
        return ApiRepsonseResult.ofFail();
    }

    /**
     * FeignException 类捕获
     */
    @ExceptionHandler(value = FeignException.class)
    public ApiRepsonseResult handlerFeignException(FeignException e) {
        outPutError(FeignException.class, CommonErrorCode.RPC_ERROR, e);
        return new ApiRepsonseResult(CommonErrorCode.RPC_ERROR.getCode(), CommonErrorCode.RPC_ERROR.getMsg());
    }

    /**
     * ClientException 类捕获
     */
    @ExceptionHandler(value = ClientException.class)
    public ApiRepsonseResult handlerClientException(ClientException e) {
        outPutError(ClientException.class, CommonErrorCode.RPC_ERROR, e);
        return new ApiRepsonseResult(CommonErrorCode.RPC_ERROR.getCode(), CommonErrorCode.RPC_ERROR.getMsg());
    }

    /**
     * ServiceException 类捕获
     */
    @ExceptionHandler(value = ServiceException.class)
    public ApiRepsonseResult handlerBusinessException(ServiceException e) {
        outPutError(ServiceException.class, CommonErrorCode.BUSINESS_ERROR, e);
        return new ApiRepsonseResult(e.getCode(), e.getMsg());
    }

    /**
     * HttpMessageNotReadableException 参数错误异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiRepsonseResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        outPutError(HttpMessageNotReadableException.class, CommonErrorCode.PARAM_ERROR, e);
        String msg = String.format("%s : 错误详情( %s )", CommonErrorCode.PARAM_ERROR.getMsg(),
                e.getRootCause().getMessage());
        return new ApiRepsonseResult(CommonErrorCode.PARAM_ERROR.getCode(), msg);
    }

    /**
     * ConstraintViolationException 参数错误异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiRepsonseResult handleConstraintViolationException(ConstraintViolationException ex) {
        String smg = "";
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (log.isDebugEnabled()) {
            for (ConstraintViolation error : constraintViolations) {
                log.error("{} -> {}", error.getPropertyPath(), error.getMessageTemplate());
                smg = error.getMessageTemplate();
            }
        }

        if (constraintViolations.isEmpty()) {
            log.error("validExceptionHandler error fieldErrors is empty");
            new ApiRepsonseResult(CommonErrorCode.BUSINESS_ERROR.getCode(), "");
        }
        return new ApiRepsonseResult(CommonErrorCode.PARAM_ERROR.getCode(), smg);
    }


    public void outPutError(Class errorType, Enum secondaryErrorType, Throwable throwable) {
        log.error("[{}] {}: {}", errorType.getSimpleName(), secondaryErrorType, throwable.getMessage(),
                throwable);
    }

    public void outPutErrorWarn(Class errorType, Enum secondaryErrorType, Throwable throwable) {
        log.warn("[{}] {}: {}", errorType.getSimpleName(), secondaryErrorType, throwable.getMessage());
    }

}
