package com.sluggard.auth.listener;

import com.sluggard.auth.events.LoginSuccessApplicationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2020/9/3 13:42
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
@Component
@Slf4j
public class ApplicationListeners {

    @Component
    public class LoginSuccessApplicationListener implements ApplicationListener<LoginSuccessApplicationEvent> {
        @Override
        public void onApplicationEvent(LoginSuccessApplicationEvent loginSuccessApplicationEvent) {
            log.info("登录成功处理器...");
            loginSuccessApplicationEvent.getTokenRequest().getRequestParameters();
        }
    }
}
