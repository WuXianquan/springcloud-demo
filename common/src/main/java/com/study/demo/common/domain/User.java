package com.study.demo.common.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author: Lon
 * @Date: 2019/6/18 16:42
 * @Description: 用户信息基础类
 */
@Proxy(lazy = false)
@Getter
@Setter
@Entity
@Table(name = "t_user")
public class User implements Serializable {

    @Id
    @Column(unique = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;

    @Column(name = "score")
    private BigDecimal score;

    @Column(name = "create_time")
    private Timestamp createTime;

    public Timestamp getCreateTime() {
        if (createTime == null) {
            return null;
        }
        return (Timestamp) createTime.clone();
    }

    public void setCreateTime(Timestamp createTime) {
        if (createTime == null) {
            this.createTime = null;
        } else {
            this.createTime = (Timestamp) createTime.clone();
        }
    }
}
