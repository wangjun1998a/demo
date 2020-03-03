package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.permission.bean.UserInfo;
import com.example.demo.permission.service.UserInfoService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author alin
 */
@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserInfoService userInfoService;

    public CustomUserDetailsServiceImpl(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
         * 1/ 通过username 获取到userInfo 信息.
         * 2/ 通过User(UserDetails) 返回UserDetails.
         */
        UserInfo userInfo = userInfoService.findByUsername(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();


        authorities.add(new SimpleGrantedAuthority("ROLE_" + userInfo.getRole().name()));

        return new User(userInfo.getUsername(), userInfo.getPassword(), authorities);
    }

}
