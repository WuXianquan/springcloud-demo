package com.study.demo.common.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Lon
 * @Date: 2020/4/21 14:16
 * @Description: domain基础类
 */
@Getter
@Setter
public class BaseDomain {

    @JsonIgnore
    private Boolean isFeign;
}
