package com.study.demo.common.exception;

import com.study.demo.common.enums.CommonErrorEnum;
import com.study.demo.common.response.ApiRepsonseResult;
import lombok.*;

/**
 * @Author: Lon
 * @Date: 2019/6/11 17:14
 * @Description:
 */
@Getter
@Setter
public class ServiceException extends RuntimeException {

    @NonNull
    private Integer code;

    private String msg;

    public ServiceException(){
        super();
    }

    public ServiceException(Integer code){
        super();
        this.code = code;
        this.msg = CommonErrorEnum.BUSINESS_ERROR.getMsg();
    }

    public ServiceException(String msg){
        super(msg);
        this.code = CommonErrorEnum.BUSINESS_ERROR.getCode();
        this.msg = msg;
    }

    public ServiceException(Integer code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(ApiRepsonseResult repsonseResult){
        super(repsonseResult.getMsg());
        this.code = repsonseResult.getCode();
        this.msg = repsonseResult.getMsg();
    }
}
