package com.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @Author: Lon
 * @Date: 2019/6/11 17:14
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceException extends RuntimeException {

    @NonNull
    private Integer code;

    private String msg;

    public ServiceException(Integer code){
        this.code = code;
        this.msg = "系统开小差";
    }

    public ServiceException(String msg){
        this.code = 9999;
        this.msg = msg;
    }
}
