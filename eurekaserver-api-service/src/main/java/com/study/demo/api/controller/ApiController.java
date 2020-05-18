package com.study.demo.api.controller;

import com.study.demo.api.annotation.PassToken;
import com.study.demo.api.annotation.UserLoginToken;
import com.study.demo.api.service.OpenService;
import com.study.demo.common.domain.Order;
import com.study.demo.api.service.OrderFeignService;
import com.study.demo.common.domain.OrderDetail;
import com.study.demo.common.dto.UserDTO;
import com.study.demo.common.util.PageHelper;
import com.study.demo.common.validate.Login;
import com.study.demo.common.validate.Register;
import com.study.demo.common.vo.TokenVO;
import com.study.demo.common.exception.ServiceException;
import com.study.demo.common.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/12 16:32
 * @Description: api接口控制器
 */
@RestController
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    private OrderFeignService orderFeignService;

    @Autowired
    private OpenService openService;

    @PassToken
    @PostMapping(value = "/register")
    public TokenVO register(@RequestBody @Validated(value = {Register.class}) UserVO userVO) {
        return openService.register(userVO);
    }

    /**
     * 用户登录，示例http://localhost:8083/user/login，参数{"username":"admin","password":"123456"}
     *
     * @param userVO
     * @return
     */
    @PassToken
    @PostMapping(value = "/login")
    public TokenVO login(@RequestBody @Validated(value = {Login.class}) UserVO userVO) {
        return openService.login(userVO);
    }

    /**
     * 查询用户个人信息
     *
     * @param userId 用户ID
     * @return
     */
    @UserLoginToken
    @GetMapping(value = "/user/id/{userId}")
    public UserDTO userInfo(@PathVariable("userId") Long userId) {
        return openService.findUserById(userId);
    }

    /**
     * 查询用户个人信息
     *
     * @param username 用户名
     * @return
     */
    @UserLoginToken
    @GetMapping(value = "/user/username/{username}")
    public UserDTO userInfo(@PathVariable("username") String username) {
        return openService.findUserByUsername(username);
    }

    /**
     * 查询商品列表
     *
     * @return
     */
    @UserLoginToken
    @GetMapping(value = "/product/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageHelper listProduct(@RequestBody PageHelper pageHelper) {
        return openService.listProduct(pageHelper);
    }

    /**
     * 查询在架商品列表
     *
     * @return
     */
    @UserLoginToken
    @GetMapping(value = "/product/listOnLine", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageHelper listOnLineProduct(@RequestBody PageHelper pageHelper) {
        return openService.listOnLineProduct(pageHelper);
    }


    @UserLoginToken
    @GetMapping(value = "/userOrder/{userId}")
    public List<Order> userOrder(@PathVariable("userId") Long userId) {
//        return orderFeignService.findUserAllOrderInfo(userId);
        return null;
    }

    @PostMapping(value = "/createOrder")
    public Order createOrder() {
        // 模拟测试
        Order order = new Order();
        OrderDetail detail = new OrderDetail();
        detail.setProductId(298614743086075904L);
        detail.setProductNumber(1);

        OrderDetail detail1 = new OrderDetail();
        detail1.setProductId(298660629273055232L);
        detail1.setProductNumber(1);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(detail);
        orderDetailList.add(detail1);

        order.setUserId(123456L);
        order.setDetailListList(orderDetailList);
        try {
            order = openService.createOrder(order);
            return order;
        } catch (ServiceException se) {
            throw new ServiceException(se.getCode(), se.getMsg());
        }
    }
}
