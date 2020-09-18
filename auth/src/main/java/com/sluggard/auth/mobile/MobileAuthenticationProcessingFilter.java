package com.sluggard.auth.mobile;

import com.sluggard.auth.events.LoginSuccessApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2020/9/2 18:05
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
public class MobileAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "username";
    public static final String SPRING_SECURITY_FORM_CODE_KEY = "code";
    public static final String SPRING_SECURITY_FORM_PRINCIPAL_KEY = "client_id";
    public static final String SPRING_SECURITY_FORM_CREDENTIALS_KEY = "client_secret";
    public static final String SPRING_SECURITY_FORM_GRANT_TYPE_KEY = "grant_type";
    public static final String GRANT_TYPE = "mobile";

    private boolean postOnly = true;

    public MobileAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/oauth/token/mobile", "POST"));
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        setAuthenticationFailureHandler((request, response, exception) -> {
        });
        setAuthenticationSuccessHandler((request, response, authentication) -> {
        });
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, ServletException {
        if (postOnly && !"POST".equalsIgnoreCase(request.getMethod())) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod(), new String[]{"POST"}
            );
        }
        String grantType = transferTrim(request.getParameter(SPRING_SECURITY_FORM_GRANT_TYPE_KEY));
        if(!GRANT_TYPE.equals(grantType)){
            return null;
        }

        // 获取参数
        String mobile = transferTrim(request.getParameter(SPRING_SECURITY_FORM_MOBILE_KEY));
        String code = transferTrim(request.getParameter(SPRING_SECURITY_FORM_CODE_KEY));
        String principal = transferTrim(request.getParameter(SPRING_SECURITY_FORM_PRINCIPAL_KEY));
        String credentials = transferTrim(request.getParameter(SPRING_SECURITY_FORM_CREDENTIALS_KEY));

        MobileAuthenticationToken authRequest = new MobileAuthenticationToken(principal, credentials, mobile, code);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request,
                              AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    protected String transferTrim(String resource){
        return (resource == null ? "" : resource).trim();
    }

}
