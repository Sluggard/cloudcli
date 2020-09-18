package com.sluggard.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sluggard.common.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lz
 */
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        ResponseResult.error(HttpStatus.FORBIDDEN.value(), "无访问权限");
        ResponseResult<Object> objectR = new ResponseResult<>();
        objectR.setCode(HttpStatus.FORBIDDEN.value());
        objectR.setMsg("无访问权限");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(objectR));
    }
}
