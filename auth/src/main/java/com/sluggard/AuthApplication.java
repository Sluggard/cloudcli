package com.sluggard;

import com.sluggard.security.config.ResourceServerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2020/9/2 17:31
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(value = "com.sluggard.*",
        excludeFilters = {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,value= ResourceServerConfiguration.class)})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
