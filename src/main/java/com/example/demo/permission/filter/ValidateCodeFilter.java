package com.example.demo.permission.filter;


import com.example.demo.permission.authentication.DemoAuthenticationFailureHandler;
import com.example.demo.permission.bean.ImageCode;
import com.example.demo.permission.controller.ValidateCodeController;
import com.example.demo.permission.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//import com.security.demo.authentication.DemoAuthenticationFailureHandler;

//import org.apache.commons.lang.StringUtils;

/**
 * 定义一个验证码的拦截器
 *
 * @author alin
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

    private DemoAuthenticationFailureHandler demoAuthenticationFailureHandler;


    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (StringUtils.equals("/login", request.getRequestURI()) &&
                StringUtils.endsWithIgnoreCase(request.getMethod(), "post")) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                demoAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    //具体的验证流程
    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }
        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
    }

    public DemoAuthenticationFailureHandler getDemoAuthenticationFailureHandler() {
        return demoAuthenticationFailureHandler;
    }

    public void setDemoAuthenticationFailureHandler(DemoAuthenticationFailureHandler demoAuthenticationFailureHandler) {
        this.demoAuthenticationFailureHandler = demoAuthenticationFailureHandler;
    }

}