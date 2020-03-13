package com.example.demo.permission.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author alin
 */
public class SessionOverDueFilter extends BasicAuthenticationFilter {

    public SessionOverDueFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public SessionOverDueFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
//    TODO 设置session过期后自动跳转

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        System.out.println(session);
//        session.getServletContext().

        super.doFilterInternal(request, response, chain);
    }
}
