package com.study.demo.user.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Author: Lon
 * @Date: 2019/6/18 17:49
 * @Description: 数据源基础类
 */
@Configuration
@PropertySource("classpath:application.yml")
@EnableJpaRepositories(basePackages = "com.study.demo.user.repository")
public class PrimaryConfig {

    @Autowired
    private Environment env;

    /**
     * 1.配置数据源
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

    /**
     * 2.配置EntityManagerFactory
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        // 配置数据源
        factory.setDataSource(dataSource());
        // VendorAdapter
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        // entity包扫描路径
        factory.setPackagesToScan("com.study.demo.common.domain");
        // jpa属性
        factory.setJpaProperties(hibernateProperties());
        factory.afterPropertiesSet();
        return factory;
    }

    /**
     * 3.事务管理器配置
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());
        return manager;
    }

    /**
     * 把HibernateExceptions转换成DataAccessExceptions
     * @return
     */
    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    /**
     * hibernate配置
     * @return
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        // 显示sql语句
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        // 格式化sql语句
        properties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        // 方言
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        return properties;
    }
}