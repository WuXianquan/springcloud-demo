package com.study.demo.user.controller;

import com.study.demo.common.domain.User;
import com.study.demo.common.validate.Login;
import com.study.demo.common.validate.Register;
import com.study.demo.common.vo.TokenVO;
import com.study.demo.common.vo.UserVO;
import com.study.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @Author: Lon
 * @Date: 2019/6/4 17:17
 * @Description:
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public TokenVO register(@RequestBody @Validated(value = {Register.class}) UserVO userVO) {
        return userService.register(userVO);
    }

    @PostMapping(value = "/login")
    public TokenVO login(@RequestBody @Validated(value = {Login.class}) UserVO userVO) {
        return userService.login(userVO);
    }

    @GetMapping(value = "/userInfo/id/{id}")
    public User userInfoById(@PathVariable("id") @NotBlank Long id) {
        return userService.findUserById(id);
    }

    @GetMapping(value = "/userInfo/username/{username}")
    public User userInfoByName(@PathVariable("username") @NotBlank String username) {
        return userService.findUserByUsername(username);
    }

    @PostMapping(value = "/addUserScore")
    public int addUserScore(@Param("id") Long id, @Param("score") BigDecimal score) {
        return userService.addUserScore(id, score);
    }

    @PostMapping(value = "/reduceUserScore")
    public int reduceUserScore(@Param("id") Long id, @Param("score") BigDecimal score) {
        return userService.reduceUserScore(id, score);
    }
}
