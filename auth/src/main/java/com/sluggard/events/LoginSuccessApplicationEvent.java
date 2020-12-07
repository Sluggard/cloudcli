package com.sluggard.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2020/9/3 13:47
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
public class LoginSuccessApplicationEvent extends ApplicationEvent {

    private TokenRequest tokenRequest;

    public LoginSuccessApplicationEvent(TokenRequest tokenRequest) {
        super(tokenRequest);
        this.tokenRequest = tokenRequest;
    }

    public TokenRequest getTokenRequest() {
        return tokenRequest;
    }
}
