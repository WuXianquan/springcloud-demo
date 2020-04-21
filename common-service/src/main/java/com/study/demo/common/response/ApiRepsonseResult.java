package com.study.demo.common.response;

import com.study.demo.common.enums.ResponseEnum;
import com.study.demo.common.util.IDGenerator;
import lombok.*;

import java.io.Serializable;

/**
 * @Author: Lon
 * @Date: 2020/4/16 12:17
 * @Description: Rest接口统一返回消息体
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiRepsonseResult implements Serializable {

    private Long queryId;

    private Integer code;

    private String msg;

    private Object body;

    public ApiRepsonseResult(Integer code, String msg) {
        this.queryId = IDGenerator.getInstance().next();
        this.code = code;
        this.msg = msg;
        this.body = "";
    }

    public ApiRepsonseResult(Integer code, String msg, Object body) {
        this.queryId = IDGenerator.getInstance().next();
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    public static ApiRepsonseResult ofSuccess() {
        return new ApiRepsonseResult(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }

    public static ApiRepsonseResult ofSuccess(Object body) {
        return new ApiRepsonseResult(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), body);
    }

    public static ApiRepsonseResult ofFail() {
        return new ApiRepsonseResult(ResponseEnum.FAIL.getCode(), ResponseEnum.FAIL.getMsg());
    }

    public static ApiRepsonseResult ofFail(Object body) {
        return new ApiRepsonseResult(ResponseEnum.FAIL.getCode(), ResponseEnum.FAIL.getMsg(), body);
    }
}
