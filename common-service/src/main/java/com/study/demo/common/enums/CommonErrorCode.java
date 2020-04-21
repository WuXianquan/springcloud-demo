package com.study.demo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @Author: Lon
 * @Date: 2019/6/11 17:22
 * @Description: 公共异常类型枚举类
 */
@Getter
@AllArgsConstructor
public enum CommonErrorCode {

    /**
     * 404 Web 服务器找不到您所请求的文件或脚本。请检查URL 以确保路径正确。
     */
    NOT_FOUND(404,"哎呀，无法找到这个资源啦"),

    /**
     * 405 对于请求所标识的资源，不允许使用请求行中所指定的方法。请确保为所请求的资源设置了正确的 MIME 类型。
     */
    METHOD_NOT_ALLOWED(405,"请换个姿势操作试试"),

    /**
     * 415 Unsupported Media Type
     */
    UNSUPPORTED_MEDIA_TYPE(415,"呀，不支持该媒体类型"),

    /**
     * 系统异常 500 服务器的内部错误
     */
    EXCEPTION(500, "服务器开小差，请稍后再试"),

    /**
     * 系统限流
     */
    TRAFFIC_LIMITING(429, "哎呀，网络拥挤请稍后再试试"),

    /**
     * 参数错误
     */
    PARAM_ERROR(100, "参数错误"),

    /**
     * 业务异常
     */
    BUSINESS_ERROR(400, "业务异常"),

    /**
     * 非法请求, token无效
     */
    ILLEGAL_REQUEST(-1, "非法请求，token无效"),

    /**
     * rpc调用异常
     */
    RPC_ERROR(510, "呀，网络出问题啦！"),

    /**
     * 服务降级
     */
    SERVICE_DOWN_ERROR(520, "服务降级"),

    /**
     * token不能为空
     */
    NO_LOGIN_ERROR(530, "token不能为空");

    private Integer code;

    private String msg;
}
