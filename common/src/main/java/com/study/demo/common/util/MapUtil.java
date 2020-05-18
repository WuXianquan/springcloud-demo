package com.study.demo.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author: Lon
 * @Date: 2020/4/24 14:18
 * @Description: Map转换工具类
 */
public class MapUtil {

    public static <T> T convertToBean(Object fromValue, Class<T> toValueType) {
        return new ObjectMapper().convertValue(fromValue, toValueType);
    }
}
