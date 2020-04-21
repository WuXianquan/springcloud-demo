package com.study.demo.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Lon
 * @Date: 2020/4/20 16:41
 * @Description: 用户登录参数类
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    private String username;

    private String password;
}
