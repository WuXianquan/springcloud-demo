package com.study.demo.order.repository;

import com.study.demo.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Lon
 * @Date: 2019/6/11 23:18
 * @Description:
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
