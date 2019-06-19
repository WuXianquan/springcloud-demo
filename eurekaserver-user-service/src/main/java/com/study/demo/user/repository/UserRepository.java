package com.study.demo.user.repository;

import com.study.demo.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @Author: Lon
 * @Date: 2019/6/18 17:10
 * @Description:
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where u.username = ?1")
    User findUserByUsername(String username);

    @Modifying
    @Query(value = "update t_user set score = score + :score where id = :id", nativeQuery = true)
    Integer addUserScore(@Param("id") Long id, @Param("score") BigDecimal score);

    @Modifying
    @Query(value = "update t_user set score = score - :score where id = :id and score >= :score", nativeQuery = true)
    Integer reduceUserScore(@Param("id")Long id, @Param("score") BigDecimal score);
}