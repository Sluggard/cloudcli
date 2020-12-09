package com.sluggard.config;

import com.sluggard.handler.LoginFailureHandler;
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
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 授权码需要开启
                .sessionManagement()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/custom/login")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(mobileAuthenticationConfig)
                .and()
                .formLogin()
                .loginPage("/custom/login")
                .loginProcessingUrl("/custom/login")
                .failureHandler(loginFailureHandler);
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
