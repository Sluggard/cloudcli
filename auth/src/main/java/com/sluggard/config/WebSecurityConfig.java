package com.sluggard.config;

import com.sluggard.checkcode.CheckCodeAuthenticationProvider;
import com.sluggard.handler.LoginFailureHandler;
import com.sluggard.mobile.MobileAuthenticationConfig;
import com.sluggard.mobile.MobileAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private UserDetailsService userDetailsService;

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
                .antMatchers("/login","/captcha","/.well-known/jwks.json")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(mobileAuthenticationConfig)
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureHandler(loginFailureHandler);
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

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(checkCodeAuthenticationProvider());
    }

    @Bean("checkCodeAuthenticationProvider")
    protected AuthenticationProvider checkCodeAuthenticationProvider() {
        return new CheckCodeAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MobileAuthenticationProcessingFilter mobileCodeAuthenticationProcessingFilter() throws Exception {
        MobileAuthenticationProcessingFilter filter = new MobileAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

}
