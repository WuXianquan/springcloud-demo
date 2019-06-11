package com.study.demo.order.repository;

import com.study.demo.order.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Lon
 * @Date: 2019/6/11 19:51
 * @Description:
 */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
