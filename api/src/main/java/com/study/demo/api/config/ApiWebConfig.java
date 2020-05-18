package com.study.demo.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Lon
 * @Date: 2020/4/17 8:50
 * @Description: 应用全局Web配置类
 */
@Configuration
public class ApiWebConfig implements WebMvcConfigurer  {

    /**
     * 设置默认返回体Content-Type
     * @param configurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}
