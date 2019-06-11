package com.study.demo.order.repository;

import com.study.demo.order.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Lon
 * @Date: 2019/6/11 16:44
 * @Description:
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
