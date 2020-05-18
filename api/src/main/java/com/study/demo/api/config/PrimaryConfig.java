package com.study.demo.api.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * @Author: Lon
 * @Date: 2019/6/18 17:49
 * @Description: 数据源基础类
 */
@PropertySource("classpath:application.yml")
public class PrimaryConfig {

    @Autowired
    private Environment env;

    /**
     * 配置数据源, 本项目只做对位开放服务，但LCN框架启动会检测数据源的存在，所以只能暂配一个
     * @return
     */
    @Bean
    public DataSource dataSource() {
        DruidDataSource source = new DruidDataSource();
        source.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
        source.setUrl(env.getRequiredProperty("spring.datasource.url"));
        source.setUsername(env.getRequiredProperty("spring.datasource.username"));
        source.setPassword(env.getRequiredProperty("spring.datasource.password"));
        return source;
    }
}