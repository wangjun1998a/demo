package com.example.demo.permission.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author alin
 * TODO 此方法暂时弃用
 */
public class JWTAuthenticationFilterCookies extends BasicAuthenticationFilter {

    public JWTAuthenticationFilterCookies(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

//从http头的Authorization 项读取token数据，
// 然后用Jwts包提供的方法校验token的合法性。如果校验通过，就认为这是一个取得授权的合法请求

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = null;
        header = request.getHeader("Authorization");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("Authorization".equals(name)) {
                    header = cookie.getValue();
                }
            }
        }
        if (header == null || !header.startsWith("Bearer//")) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        Cookie[] cookies = null;
        String token = null;
        token = request.getHeader("Authorization");
        cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("Authorization".equals(name)) {
                    token = cookie.getValue();
                }
            }
        }
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(token.replace("Bearer//", ""))
                    .getBody();
            String user = claims.getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, "123456", new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
