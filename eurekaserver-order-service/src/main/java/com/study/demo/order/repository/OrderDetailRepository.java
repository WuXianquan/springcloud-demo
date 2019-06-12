package com.study.demo.order.repository;

import com.study.demo.order.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/6/11 19:51
 * @Description:
 */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query(value = "select d from t_order_detail d where d.orderId = ?1")
    List<OrderDetail> findOrderDetailListByOrderId(Long orderId);
}
