package com.sluggard.config;

import com.sluggard.handler.CustomAccessDeniedHandler;
import com.sluggard.handler.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class ResourceServerConfig {
    @Value("${gatewayWhiteList:''}")
    private String[] whiteList;

    @Autowired
    private CustomAccessDeniedHandler deniedHandler;

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .httpBasic().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .pathMatchers(
                        "/doc**",
                        "/swagger**/**",
                        "/api/*/v2/**",
                        "/webjars/**",
                        "/api/auth/**",
                        "/actuator/**")
                .permitAll()
                .pathMatchers(whiteList)
                .permitAll()
                .anyExchange().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(deniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and().csrf().disable()
        ;
        // 开起jwt需打开
        http.oauth2ResourceServer()
                .accessDeniedHandler(deniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .jwt();
        return http.build();
    }



}
