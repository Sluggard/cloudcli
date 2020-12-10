package com.sluggard.service.impl;

import com.sluggard.feign.client.UserClient;
import com.sluggard.feign.vo.Customer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2020/9/2 15:46
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021
 */
@Component
public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(StringUtils.isBlank(username)){
            throw new BadCredentialsException("用户名或密码错误");
        }
        Customer customer = userClient.loadUserByUsername(username);
        if(Objects.isNull(customer)){
            throw new BadCredentialsException("用户不存在！");
        }

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<String> roles = customer.getRoles();
        if(Objects.nonNull(roles) && !roles.isEmpty()) {
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }

        return new User(customer.getUsername(),
                customer.getPassword(),
                !customer.getDisable(),
                true,
                true,
                !customer.getLocked(),
                authorities);
    }

}
