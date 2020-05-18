package com.study.demo.order.repository;

import com.study.demo.common.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author: Lon
 * @Date: 2020/5/15 12:03
 * @Description:
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "select c from Cart c where c.userId = ?1")
    Cart findByUserId(Long userId);
}
