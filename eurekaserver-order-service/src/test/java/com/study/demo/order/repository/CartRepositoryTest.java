package com.study.demo.order.repository;

import com.study.demo.common.domain.Cart;
import com.study.demo.common.domain.CartItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Temporal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartRepositoryTest {

    private static final Long cartId = 123456789L;

    private static final Long userId = 123456789L;

    private static final Long productId = 123456789L;

    private static final Long cartItemId = 123456789L;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    public void save(){
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setItemId(cartItemId);
        cartItem.setCartId(cartId);
        cartItem.setProductId(productId);
        cartItem.setProductNum(1);
        cartItems.add(cartItem);
        cartItemRepository.saveAndFlush(cartItem);

        Cart c = new Cart();
        c.setId(cartId);
        c.setUserId(userId);
        c.setCartItems(cartItems);
        Cart r = cartRepository.saveAndFlush(c);
        Assert.assertEquals(userId.longValue(), r.getUserId().longValue());
    }

    @Test
    public void findByUserId(){
        Cart cart = cartRepository.findByUserId(userId);
        Assert.assertNotNull(cart);
    }

    @Test
    public void delete(){
        cartItemRepository.deleteById(cartItemId);
        cartRepository.deleteById(cartId);
    }
}