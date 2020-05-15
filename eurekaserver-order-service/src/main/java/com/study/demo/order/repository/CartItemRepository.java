package com.study.demo.order.repository;

import com.study.demo.common.domain.CartItem;
import com.study.demo.common.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2020/5/15 12:03
 * @Description:
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
