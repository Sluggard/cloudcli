package com.sluggard.feign.intercepter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.common.constants
 * @date 2020/3/3 11:01
 * @Copyright © 2019-2021
 */

@Slf4j
@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("请求地址：{}, 请求参数：{}", requestTemplate.url(), requestTemplate.queries());
        try {
            Map<String, String> headers = getHeaders();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestTemplate.header(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            log.error("Feign 拦截器 异常", e);
        }
    }

    private Map<String, String> getHeaders() {
        Map<String, String> map = new LinkedHashMap<>();
        if (RequestContextHolder.getRequestAttributes() == null) {
            return map;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

}