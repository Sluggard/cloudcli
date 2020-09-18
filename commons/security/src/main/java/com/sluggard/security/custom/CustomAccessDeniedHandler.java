package com.sluggard.security.custom;


import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.AbstractOAuth2SecurityExceptionHandler;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义访问拒绝
 */
@Component
public class CustomAccessDeniedHandler extends AbstractOAuth2SecurityExceptionHandler implements AccessDeniedHandler {

    private WebResponseExceptionTranslator<OAuth2Exception> exceptionTranslator = new DefaultWebResponseExceptionTranslator();

    private OAuth2ExceptionRenderer exceptionRenderer = new DefaultOAuth2ExceptionRenderer();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        try {
            ResponseEntity<OAuth2Exception> translate = exceptionTranslator.translate(authException);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", translate.getStatusCodeValue());
            jsonObject.put("msg", translate.getBody().getMessage());
            jsonObject.put("timestamps", System.currentTimeMillis());
            ResponseEntity<?> responseEntity = enhanceResponse(translate, authException);
            HttpHeaders headers = responseEntity.getHeaders();
            HttpStatus statusCode = responseEntity.getStatusCode();
            ResponseEntity<JSONObject> rResponseEntity = new ResponseEntity<>(jsonObject, headers, statusCode);
            exceptionRenderer.handleHttpEntityResponse(rResponseEntity, new ServletWebRequest(request, response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
