package com.sluggard.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sluggard.auth.entity.OauthClientDetails;
import com.sluggard.auth.mapper.OauthClientDetailsMapper;
import com.sluggard.auth.service.OauthClientDetailsService;
import com.sluggard.common.utls.AssertHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author lizheng
 * @since 2020-09-17
 */
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

}
