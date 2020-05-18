package com.study.demo.common.vo;

import com.study.demo.common.validate.Login;
import com.study.demo.common.validate.Register;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

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

    @NotBlank(message = "用户名不能为空", groups = {Register.class, Login.class})
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 10, message = "密码6~10位", groups = {Register.class, Login.class})
    private String password;

    @NotBlank(message = "确认密码不能为空", groups = {Register.class})
    private String checkPassword;
}
