package com.sluggard.common.handler;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.auth.config
 * @date 2020/5/28 10:46
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>(1);
//            OauthUser oauthUser = (OauthUser) authentication.getPrincipal();
//            if(oauthUser != null) {
//                additionalInfo.put("license", SecurityConstants.LICENSE);
//                additionalInfo.put("status", oauthUser.getTStatus());
//                additionalInfo.put("userId", oauthUser.getOperId());
//                additionalInfo.put("deptName", oauthUser.getDeptName());
//                additionalInfo.put("deptType", oauthUser.getDeptType());
//            }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken ;
    }
}
