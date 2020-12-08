package com.sluggard.filter;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.function.Supplier;


/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2020/9/7 13:26
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
@Slf4j
@Component
@RefreshScope
public class StringTrimGlobalFilter implements GlobalFilter, Ordered {

    private static final String CHARSET = "UTF-8";

    private final List<HttpMessageReader<?>> messageReaders;

    public StringTrimGlobalFilter() {
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 打印请求路径

        ServerHttpRequest request = exchange.getRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        headers.remove(HttpHeaders.CONTENT_LENGTH);

        URI newUri = convertUrlParams(request);

        // 获取参数
        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
        // mediaType
        MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
        // 读取并修改请求体数据
        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                .flatMap(body -> {
                    log.info(String.format("\nMethod:{%s}\nHost:{%s}\nPath:{%s}\nQueryParam:{%s}\nBody:{%s}",
                            exchange.getRequest().getMethod().name(),
                            exchange.getRequest().getURI().getHost(),
                            exchange.getRequest().getURI().getPath(),
                            exchange.getRequest().getQueryParams(),body));
                    return Mono.just(body);
                });

        BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);

        // 构造新请求 将请求写回到请求中
        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        return bodyInserter.insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> {
                    ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(
                            exchange.getRequest()) {
                        @Override
                        public HttpHeaders getHeaders() {
                            long contentLength = headers.getContentLength();
                            HttpHeaders httpHeaders = new HttpHeaders();
                            httpHeaders.putAll(super.getHeaders());
                            if (contentLength > 0) {
                                httpHeaders.setContentLength(contentLength);
                            } else {
                                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                            }
                            return httpHeaders;
                        }

                        @Override
                        public Flux<DataBuffer> getBody() {
                            return outputMessage.getBody();
                        }
                    };
                    ServerHttpRequest serverHttpRequest = exchange
                            .mutate()
                            .request(decorator)
                            .build()
                            .getRequest()
                            .mutate()
                            .uri(newUri)
                            .build();
                    return chain.filter(exchange
                            .mutate()
                            .request(serverHttpRequest)
                            .build());
                }));
    }

    /**
     * 去除？参数中以及地址链接的空格
     *
     * @param request
     * @return
     */
    private URI convertUrlParams(ServerHttpRequest request) {
        URI uri = request.getURI();
        String originalQuery = uri.getRawQuery();
        StringBuffer query = new StringBuffer();

        handlerQueryParams(originalQuery, query);

        String path = handlerUri(uri);

        return UriComponentsBuilder.fromUri(uri)
                .replacePath(path)
                .replaceQuery(query.toString()).build(true).toUri();
    }

    /**
     * 处理地址链接
     * @param uri
     * @return
     */
    private String handlerUri(URI uri) {
        String path = uri.getPath();
        String[] strings = StringUtils.split(path, "/");
        for (int i = 0; i < strings.length; i++) {
            strings[i] = urlDecodeTrim(strings[i]);
        }
        path = Joiner.on("/").join(strings);
        return path;
    }

    /**
     * 处理？参数
     * @param originalQuery
     * @param query
     */
    private void handlerQueryParams(String originalQuery, StringBuffer query) {
        if(StringUtils.isBlank(originalQuery)){
            return;
        }
        String[] params = originalQuery.split("&");
        for (String param : params) {
            String[] keyValues = param.split("=");
            if (keyValues.length != 2) {
                continue;
            }

            query.append(keyValues[0])
                    .append("=")
                    .append(urlDecodeTrim(keyValues[1]));

        }
    }

    private String urlDecodeTrim(String source){
        String result = "";
        try {
            result = URLEncoder.encode(URLDecoder.decode(source, CHARSET).trim(), CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.error("请求链接解码错误", e);
        }
        return result;
    }

    @Override
    public int getOrder() {
        return 0;
    }


    class CachedBodyOutputMessage implements ReactiveHttpOutputMessage {

        private final DataBufferFactory bufferFactory;

        private final HttpHeaders httpHeaders;

        private Flux<DataBuffer> body = Flux.error(new IllegalStateException(
                "The body is not set. " + "Did handling complete with success?"));

        CachedBodyOutputMessage(ServerWebExchange exchange, HttpHeaders httpHeaders) {
            this.bufferFactory = exchange.getResponse().bufferFactory();
            this.httpHeaders = httpHeaders;
        }

        @Override
        public void beforeCommit(Supplier<? extends Mono<Void>> action) {

        }

        @Override
        public boolean isCommitted() {
            return false;
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.httpHeaders;
        }

        @Override
        public DataBufferFactory bufferFactory() {
            return this.bufferFactory;
        }

        /**
         * Return the request body, or an error stream if the body was never set or when.
         *
         * @return body as {@link Flux}
         */
        public Flux<DataBuffer> getBody() {
            return this.body;
        }

        @Override
        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            this.body = Flux.from(body);
            return Mono.empty();
        }

        @Override
        public Mono<Void> writeAndFlushWith(
                Publisher<? extends Publisher<? extends DataBuffer>> body) {
            return writeWith(Flux.from(body).flatMap(p -> p));
        }

        @Override
        public Mono<Void> setComplete() {
            return writeWith(Flux.empty());
        }

    }

}
