package com.sluggard.handler;

import com.alibaba.fastjson.JSONObject;
import com.sluggard.common.vo.ResponseResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Desc 自定义没有访问权限时返回数据
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.gateway.handler
 * @date 2020/5/29 10:01
 * @Copyright © 2019-2021
 */
@Component
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        return Mono.defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> {
                    response.setStatusCode(this.getStatus(denied));
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);

                    DataBufferFactory dataBufferFactory = response.bufferFactory();
                    ResponseResult<Object> responseResult = ResponseResult.error(ResponseResult.RESPONSE_RESULT_CODE_FORBIDDEN, "无访问权限");
                    DataBuffer buffer = dataBufferFactory.wrap(JSONObject.toJSONString(responseResult).getBytes());

                    return response.writeWith(Mono.just(buffer))
                            .doOnError( error -> DataBufferUtils.release(buffer));
                });
    }

    private HttpStatus getStatus(Exception authException) {
        if (authException instanceof OAuth2AuthenticationException) {
            OAuth2Error error = ((OAuth2AuthenticationException)authException).getError();
            if (error instanceof BearerTokenError) {
                return ((BearerTokenError)error).getHttpStatus();
            }
        }

        return HttpStatus.FORBIDDEN;
    }

}
