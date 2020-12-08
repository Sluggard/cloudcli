package com.sluggard.config;

import com.sluggard.mobile.MobileAuthenticationConfig;
import com.sluggard.mobile.MobileAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lizheng
 * @version V1.0
 * @Description: security安全配置类
 * @Copyright © 2019-2021
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().disable()
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .apply(mobileAuthenticationConfig);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 需要配置这个支持password模式
     * support password grant type
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public MobileAuthenticationProcessingFilter mobileCodeAuthenticationProcessingFilter() throws Exception {
        MobileAuthenticationProcessingFilter filter = new MobileAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

}
