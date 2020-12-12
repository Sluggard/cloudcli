package com.sluggard.service.impl;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sluggard.entity.OauthClientDetails;
import com.sluggard.mapper.OauthClientDetailsMapper;
import com.sluggard.service.OauthClientDetailsService;
import com.sluggard.common.utls.AssertHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lizheng
 * @since 2020-09-17
 */
@Slf4j
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(OauthClientDetails oauthClientDetails) {
        OauthClientDetails clientDetails = this.getOne(new LambdaQueryWrapper<OauthClientDetails>().eq(OauthClientDetails::getClientId, oauthClientDetails.getClientId()));
        AssertHelper.fail(Objects.nonNull(clientDetails), "客户端ID已存在，请重新输入");

        // 密码加密存储
        oauthClientDetails.setClientSecret(passwordEncoder.encode(oauthClientDetails.getClientSecret()));

        this.save(oauthClientDetails);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetails oauthClientDetails = this.getOne(new LambdaQueryWrapper<OauthClientDetails>().eq(OauthClientDetails::getClientId, clientId));
        BaseClientDetails baseClientDetails = new BaseClientDetails(oauthClientDetails.getClientId(),
                oauthClientDetails.getResourceIds(),
                oauthClientDetails.getScope(),
                oauthClientDetails.getAuthorizedGrantTypes(),
                oauthClientDetails.getAuthorities(),
                oauthClientDetails.getWebServerRedirectUri());

        baseClientDetails.setClientSecret(oauthClientDetails.getClientSecret());
        baseClientDetails.isAutoApprove(oauthClientDetails.getAutoapprove().toString());
        if (oauthClientDetails.getAccessTokenValidity() != null) {
            baseClientDetails.setAccessTokenValiditySeconds(oauthClientDetails.getAccessTokenValidity());
        }

        if (oauthClientDetails.getRefreshTokenValidity() != null) {
            baseClientDetails.setRefreshTokenValiditySeconds(oauthClientDetails.getRefreshTokenValidity());
        }

        String json = oauthClientDetails.getAdditionalInformation();
        if (json != null) {
            try {
                Map<String, Object> additionalInformation = (Map<String, Object>) new JSONObject().get(json, Map.class);
                baseClientDetails.setAdditionalInformation(additionalInformation);
            } catch (Exception var6) {
                log.warn("Could not decode JSON for additional information: " + baseClientDetails, var6);
            }
        }

        String scopes = oauthClientDetails.getScope();
        if (scopes != null) {
            baseClientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(scopes));
        }

        return baseClientDetails;
    }
}
