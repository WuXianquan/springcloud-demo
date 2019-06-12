package com.study.demo.order.service.impl;

import com.study.demo.order.domain.OrderDetail;
import com.study.demo.order.service.OrderDetailService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/12 13:44
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailServiceImplTest {

    private static final Long orderId = 298614743086075905L;
    private static final Long productId = 298614743086075904L;
    private static final Long productId1 = 298660629273055232L;
    private static final BigDecimal productPrice = BigDecimal.valueOf(69.55);
    private static final BigDecimal productPrice1 = BigDecimal.valueOf(79.33);

    @Autowired
    private OrderDetailService orderDetailService;

    @Test
    public void findOrderDetailById() {
        OrderDetail detail = orderDetailService.findOrderDetailById(298670090823077888L);
        Assert.assertEquals(298670090823077888L, detail.getId().longValue());
    }

    @Test
    public void findOrderDetailListByOrderId() {
        List<OrderDetail> orderDetailList = orderDetailService.findOrderDetailListByOrderId(orderId);
        Assert.assertNotEquals(0, orderDetailList.size());
    }

    @Test
    public void createOrderDetail() {
        OrderDetail detail = new OrderDetail();
        detail.setOrderId(orderId);
        detail.setProductId(productId);
        detail.setProductPrice(productPrice);
        detail.setProductNumber(5);
        detail.setAmount(new BigDecimal(5).multiply(productPrice));
        orderDetailService.createOrderDetail(detail);
        Assert.assertNotNull(detail.getId());
    }

    @Test
    public void createOrderDetailList() {
        OrderDetail detail = new OrderDetail();
        detail.setOrderId(orderId);
        detail.setProductId(productId);
        detail.setProductPrice(productPrice);
        detail.setProductNumber(5);
        detail.setAmount(new BigDecimal(5).multiply(productPrice));

        OrderDetail detail1 = new OrderDetail();
        detail1.setOrderId(orderId);
        detail1.setProductId(productId1);
        detail1.setProductPrice(productPrice1);
        detail1.setProductNumber(3);
        detail1.setAmount(new BigDecimal(3).multiply(productPrice1));

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(detail);
        orderDetailList.add(detail1);

        orderDetailService.createOrderDetailList(orderDetailList);
        Assert.assertNotNull(detail.getId());
        Assert.assertNotNull(detail1.getId());
    }
}