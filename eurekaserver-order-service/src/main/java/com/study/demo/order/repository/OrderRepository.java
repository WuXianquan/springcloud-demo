package com.study.demo.order.repository;

import com.common.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/11 23:18
 * @Description:
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select o from t_order o where o.userId = ?1")
    List<Order> findUserAllOrderInfo(Long userId);
}
