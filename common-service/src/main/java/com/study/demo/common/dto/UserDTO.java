package com.study.demo.common.dto;

import com.study.demo.common.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @Author: Lon
 * @Date: 2019/6/18 17:37
 * @Description: 用户信息传输类
 */
@Proxy(lazy = false)
@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;

    private String username;

    private Integer status;

    private BigDecimal score;

    private String createTime;

    public UserDTO(User user) {
        if (user != null) {
            BeanUtils.copyProperties(user, this);
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (user.getCreateTime() != null) {
                this.createTime = sdf.format(user.getCreateTime());
            }
        }
    }
}
