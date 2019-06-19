package com.study.demo.order.repository;

import com.study.demo.common.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: Lon
 * @Date: 2019/6/11 16:44
 * @Description:
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query(value = "update t_product set stock = stock - :amount, frozen_stock = frozen_stock + :amount where id = :id and stock >= :amount", nativeQuery = true)
    int reduceProductStock(@Param("id") Long productId, @Param("amount") Long amount);

    @Modifying
    @Query(value = "update t_product set stock = stock + :amount, frozen_stock = frozen_stock - :amount where id = :id", nativeQuery = true)
    int addProductStock(@Param("id") Long productId, @Param("amount") Long amount);
}
