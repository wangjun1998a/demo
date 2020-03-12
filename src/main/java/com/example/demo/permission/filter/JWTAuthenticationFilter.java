package com.example.demo.permission.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author alin
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

//从http头的Authorization 项读取token数据，
// 然后用Jwts包提供的方法校验token的合法性。如果校验通过，就认为这是一个取得授权的合法请求

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = "";
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            String name = cookies[i].getName();
            if ("Authorization".equals(name)) {
                header = cookies[i].getValue();
            }
        }
        if (header == null || !header.startsWith("Bearer-")) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = "";
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            String name = cookies[i].getName();
            if ("Authorization".equals(name)) {
                token = cookies[i].getValue();
            }
        }
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey("MyJwtSecret")
                    .parseClaimsJws(token.replace("Bearer-", ""))
                    .getBody();
            String user = claims.getSubject();
//            @SuppressWarnings("unchecked")
//            List<String> roles = claims.get("role", List.class);
//            List<SimpleGrantedAuthority> auth = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }


//            // parse the token.
//            String user = Jwts.parser()
//                    .setSigningKey("MyJwtSecret")
//                    .parseClaimsJws(token.replace("Bearer-", ""))
//                    .getBody()
//                    .getSubject();
//
//            if (user != null) {
//                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
//            }
            return null;
        }
        return null;
    }
}
