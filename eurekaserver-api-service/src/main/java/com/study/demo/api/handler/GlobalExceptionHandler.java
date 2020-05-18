package com.study.demo.api.handler;

import com.netflix.client.ClientException;
import com.study.demo.common.enums.CommonErrorEnum;
import com.study.demo.common.exception.ServiceException;
import com.study.demo.common.response.ApiRepsonseResult;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


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
        outPutErrorWarn(NoHandlerFoundException.class, CommonErrorEnum.NOT_FOUND, exception);
        return new ApiRepsonseResult(CommonErrorEnum.NOT_FOUND.getCode(), CommonErrorEnum.NOT_FOUND.getMsg());
    }

    /**
     * HttpRequestMethodNotSupportedException 405 异常处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiRepsonseResult handlerHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception) {
        outPutErrorWarn(HttpRequestMethodNotSupportedException.class,
                CommonErrorEnum.METHOD_NOT_ALLOWED, exception);
        return new ApiRepsonseResult(CommonErrorEnum.METHOD_NOT_ALLOWED.getCode(), CommonErrorEnum.METHOD_NOT_ALLOWED.getMsg());
    }

    /**
     * HttpMediaTypeNotSupportedException 415 异常处理
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ApiRepsonseResult handlerHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception) {
        outPutErrorWarn(HttpMediaTypeNotSupportedException.class,
                CommonErrorEnum.UNSUPPORTED_MEDIA_TYPE, exception);
        return new ApiRepsonseResult(CommonErrorEnum.UNSUPPORTED_MEDIA_TYPE.getCode(), CommonErrorEnum.UNSUPPORTED_MEDIA_TYPE.getMsg());
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
        outPutError(Exception.class, CommonErrorEnum.EXCEPTION, throwable);
        return ApiRepsonseResult.ofFail();
    }

    /**
     * FeignException 类捕获
     */
    @ExceptionHandler(value = FeignException.class)
    public ApiRepsonseResult handlerFeignException(FeignException e) {
        outPutError(FeignException.class, CommonErrorEnum.RPC_ERROR, e);
        return new ApiRepsonseResult(CommonErrorEnum.RPC_ERROR.getCode(), CommonErrorEnum.RPC_ERROR.getMsg());
    }

    /**
     * ClientException 类捕获
     */
    @ExceptionHandler(value = ClientException.class)
    public ApiRepsonseResult handlerClientException(ClientException e) {
        outPutError(ClientException.class, CommonErrorEnum.RPC_ERROR, e);
        return new ApiRepsonseResult(CommonErrorEnum.RPC_ERROR.getCode(), CommonErrorEnum.RPC_ERROR.getMsg());
    }

    /**
     * ServiceException 类捕获
     */
    @ExceptionHandler(value = ServiceException.class)
    public ApiRepsonseResult handlerBusinessException(ServiceException e) {
        outPutError(ServiceException.class, CommonErrorEnum.BUSINESS_ERROR, e);
        return new ApiRepsonseResult(e.getCode(), e.getMsg());
    }

    /**
     * API controller请求参数校验不通过异常 捕获
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiRepsonseResult exceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return new ApiRepsonseResult(CommonErrorEnum.PARAM_ERROR.getCode(), bindingResult.getFieldErrors()
                .get(0).getDefaultMessage());
    }


    public void outPutError(Class errorType, Enum secondaryErrorType, Throwable throwable) {
        log.error("[{}] {}: {}", errorType.getSimpleName(), secondaryErrorType, throwable.getMessage(),
                throwable);
    }

    public void outPutErrorWarn(Class errorType, Enum secondaryErrorType, Throwable throwable) {
        log.warn("[{}] {}: {}", errorType.getSimpleName(), secondaryErrorType, throwable.getMessage());
    }

}
