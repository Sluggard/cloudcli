package com.sluggard.handler;

import com.alibaba.fastjson.JSONObject;
import com.sluggard.common.vo.ResponseResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Desc 自定义未登录返回
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.gateway.handler
 * @date 2020/5/29 10:05
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
@Component
public class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException authException) {
        return Mono.defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> {
                    response.setStatusCode(this.getStatus(authException));
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);

                    DataBufferFactory dataBufferFactory = response.bufferFactory();
                    ResponseResult responseResult = ResponseResult.error(ResponseResult.RESPONSE_RESULT_CODE_UNAUTHORIZED, "用户未登录!");
                    DataBuffer buffer = dataBufferFactory.wrap(JSONObject.toJSONString(responseResult).getBytes());

                    return response.writeWith(Mono.just(buffer))
                            .doOnError( error -> DataBufferUtils.release(buffer));
                });

    }

    private HttpStatus getStatus(AuthenticationException authException) {
        if (authException instanceof OAuth2AuthenticationException) {
            OAuth2Error error = ((OAuth2AuthenticationException)authException).getError();
            if (error instanceof BearerTokenError) {
                return ((BearerTokenError)error).getHttpStatus();
            }
        }

        return HttpStatus.UNAUTHORIZED;
    }

}
