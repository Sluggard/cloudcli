package com.sluggard.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.sluggard.common.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.auth.config
 * @date 2020/5/28 10:49
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
@Component
@RefreshScope
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpServletResponse response;

    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());

        ResponseResult<Object> error = ResponseResult.error(ResponseResult.RESPONSE_RESULT_CODE_ERROR, e.getMessage());
        if ("User is disabled".equals(e.getMessage())) {
            error
                    .setCode(ResponseResult.RESPONSE_RESULT_CODE_UNAUTHORIZED)
                    .setMsg("当前用户被禁用，请联系管理员");
        } else if ("Bad credentials".equals(e.getMessage())) {
            error
                    .setCode(ResponseResult.RESPONSE_RESULT_CODE_UNAUTHORIZED)
                    .setMsg("手机号与密码不匹配,请重新输入");
        } else if (e.getMessage().contains("Invalid refresh token")) {
            error
                    .setCode(ResponseResult.RESPONSE_RESULT_CODE_UNAUTHORIZED)
                    .setMsg("refresh_token无效");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        response.getWriter().write(objectMapper.writeValueAsString(error));
        response.getWriter().close();
        return null;
    }
}
