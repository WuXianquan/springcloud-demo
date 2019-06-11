package com.study.demo.order.repository;

import com.study.demo.order.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Lon
 * @Date: 2019/6/11 16:44
 * @Description:
 */
@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query(value = "update t_product set stock = stock - :amount, frozen_stock = frozen_stock + :amount where id = :id and stock > amount")
    int reduceProductStock(@Param("id") Long productId, @Param("amount") Long amount);

    @Modifying
    @Query(value = "update t_product set stock = stock + :amount, frozen_stock = frozen_stock - :amount where id = :id")
    int addProductStock(@Param("id") Long productId, @Param("amount") Long amount);
}
