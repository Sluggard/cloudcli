package com.sluggard.security.util;

import com.alibaba.fastjson.JSONObject;
import com.sluggard.security.cnstant.SecurityConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author lizheng
 */
public class SecurityHelper {

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * 获取用户
     */
    public static UserDetails getUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if(principal != null && !"anonymousUser".equals(principal)){
           return JSONObject.parseObject(principal.toString(), UserDetails.class);
        }
        return null;
    }

    /**
     * 获取用户角色信息
     *
     * @return 角色集合
     */
    public static List<String> getRoles() {
        Authentication authentication = getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roleIds = new ArrayList<>();
        authorities.stream()
                .filter(granted -> StringUtils.startsWith(granted.getAuthority(), SecurityConstants.ROLE))
                .forEach(granted -> {
                    String roleName = StringUtils.removeStart(granted.getAuthority(), SecurityConstants.ROLE);
                    roleIds.add(roleName);
                });
        return roleIds;
    }
}
