package com.sluggard.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sluggard.entity.OauthClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author lizheng
 * @since 2020-09-17
 */
public interface OauthClientDetailsService extends IService<OauthClientDetails>, ClientDetailsService {

    /**
     * 客户端注册
     * @param oauthClientDetails
     * @return
     */
    void register(OauthClientDetails oauthClientDetails);
}
