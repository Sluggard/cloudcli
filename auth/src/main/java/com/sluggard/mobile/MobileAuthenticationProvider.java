package com.sluggard.mobile;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2020/9/2 13:43
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    public static final String LOGIN_USER_PREFIX = "LOGIN:USER:";

    private UserDetailsService userDetailsService;

    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken authenticationToken = (MobileAuthenticationToken) authentication;
        String username = (String) authenticationToken.getUsername();
        String password = (String) authenticationToken.getCode();
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("用户不存在");
        }
        String code = stringRedisTemplate.opsForValue().get(LOGIN_USER_PREFIX + username);
        if(StringUtils.isBlank(code) || !StringUtils.equals(code, password)){
            throw new BadCredentialsException("手机号或密码错误");
        }
        return  new MobileAuthenticationToken(username, user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
}
