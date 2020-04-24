package com.study.demo.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Lon
 * @Date: 2019/6/4 17:17
 * @Description: 用户登录token信息类
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenVO {

    /**
     * 获取到的凭证
     */
    private String token;

    /**
     * 凭证有效时间,单位：秒
     */
    private Long expireTime;
}
