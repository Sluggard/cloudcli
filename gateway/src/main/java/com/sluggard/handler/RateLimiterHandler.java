package com.sluggard.handler;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.gateway.handler
 * @date 2020/1/15 15:54
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
@Configuration
public class RateLimiterHandler {

    /**
     * 限流 获取请求用户ip作为限流key
     * @return
     */
    @Bean
    public KeyResolver ipKeyResolver(){
        System.out.println("##############ipKeyResolver########################");
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}
