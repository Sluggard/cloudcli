package com.sluggard.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sluggard.entity.OauthClientDetails;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author lizheng
 * @since 2020-09-17
 */
public interface OauthClientDetailsService extends IService<OauthClientDetails> {

    /**
     * 客户端注册
     * @param oauthClientDetails
     * @return
     */
    void register(OauthClientDetails oauthClientDetails);
}
