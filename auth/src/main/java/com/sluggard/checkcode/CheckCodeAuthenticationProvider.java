package com.sluggard.checkcode;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

/**
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.auth.provider
 * @date 2020/5/29 14:58
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
public class CheckCodeAuthenticationProvider extends DaoAuthenticationProvider {

    private static final String CHECK_GRANT_TYPE ="password";
    private static final String CHECK_CODE_PREFIX ="checkCodePrefix";
    private static final String CHECK_CODE ="checkCode";
    private static final String GRANT_TYPE ="grant_type";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public CheckCodeAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        LinkedHashMap<String, String> details = (LinkedHashMap<String, String>) authentication.getDetails();
        String grantType = details.get(GRANT_TYPE);
        if(StringUtils.isNotBlank(grantType)  && StringUtils.equalsIgnoreCase(grantType, CHECK_GRANT_TYPE)){
            String checkCodePrefix = details.get(CHECK_CODE_PREFIX);
            String checkCode = details.get(CHECK_CODE);

            if(StringUtils.isBlank(checkCode) || !StringUtils.equalsIgnoreCase(checkCode, redisTemplate.opsForValue().get(checkCodePrefix))){
                redisTemplate.delete(checkCodePrefix);
                throw new BadCredentialsException("图片验证码错误");
            }
        }


        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
