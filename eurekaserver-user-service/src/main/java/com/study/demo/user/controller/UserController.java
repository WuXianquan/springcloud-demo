package com.study.demo.user.controller;

import com.study.demo.common.dto.UserDTO;
import com.study.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/userInfo/{id}")
    public UserDTO userInfo(@PathVariable("id") Long id) {
        return new UserDTO(userService.findUserById(id));
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
