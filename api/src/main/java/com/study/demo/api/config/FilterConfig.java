package com.study.demo.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2020/4/16 13:58
 * @Description: 应用全局过滤配置类，用于判断是否对接口返回做统一处理
 */
@Getter
@Setter
@Configuration
@PropertySource(value = "classpath:dispose.yml", encoding = "UTF-8")
public class FilterConfig {

    public static final String PREFIX = "dispose";

    /**
     * 统一返回过滤包
     */
    private List<String> adviceFilterPackage = new ArrayList<>();

    /**
     * 统一返回过滤类
     */
    private List<String> adviceFilterClass = new ArrayList<>();
}
