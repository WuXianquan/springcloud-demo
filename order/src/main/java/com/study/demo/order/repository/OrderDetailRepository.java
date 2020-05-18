package com.study.demo.order.repository;

import com.study.demo.common.domain.OrderDetail;
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

    @Query(value = "select o from OrderDetail o where o.orderId = ?1")
    List<OrderDetail> findOrderDetailListByOrderId(Long orderId);
}
