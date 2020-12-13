package com.sluggard.mybatis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author lizheng
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class TemplateDataSourceConfig{
    private String username;
    private String password;
    private String driverClassName;
    private String url;
}
