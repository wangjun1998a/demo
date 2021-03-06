package com.example.demo.permission.service.impl;

import com.example.demo.permission.authentication.DemoAuthenticationFailureHandler;
import com.example.demo.permission.authentication.DemoAuthenticationSuccessHandler;
import com.example.demo.permission.filter.JWTAuthenticationFilter;
import com.example.demo.permission.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author alin
 * 打开@EnableGlobalMethodSecurity(prePostEnabled = true)就会打开方法的权限控制。
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyWebSecurityConfigurerAdapterImpl extends WebSecurityConfigurerAdapter {

    @Qualifier("customUserDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DemoAuthenticationFailureHandler demoAuthenticationFailureHandler;
    @Autowired
    private DemoAuthenticationSuccessHandler demoAuthenticationSuccessHandler;

    @Bean
    public JWTAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JWTAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setDemoAuthenticationFailureHandler(demoAuthenticationFailureHandler);
//        关闭CSRF跨域 还有iframe的展示问题
        http.cors().and()
                .headers().frameOptions().disable()
                .and()
                .logout()
                .and()
                .csrf(AbstractHttpConfigurer::disable);
//        最大登录限制
        http.sessionManagement().maximumSessions(1).expiredUrl("/login");
        http.formLogin()
                .loginPage("/login")
//                .loginProcessingUrl("/login")
                .successForwardUrl("/index")
                .defaultSuccessUrl("/index")
//                .successHandler(demoAuthenticationSuccessHandler)
//                .failureHandler(demoAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/authentication").permitAll()
                .antMatchers("/getCodeMsg").permitAll()
//                .antMatchers("/").permitAll()
//                .antMatchers("/").permitAll()
                .antMatchers("/code/image").permitAll()
                .antMatchers("/js/*.js").permitAll()
                .antMatchers("/layui/**").permitAll()
//                .antMatchers("/login.html").permitAll()
                .anyRequest().authenticated();
//        关闭缓存
//        http.headers().cacheControl();
//        http.addFilter(new JWTLoginFilter(authenticationManager()));
//        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //在UsernamePasswordAuthenticationFilter添加新添加的拦截器，验证码的拦截器
//        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);

//        http.cors();
//        http.headers().frameOptions().disable();
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.formLogin().loginPage("/login");
//        http.authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/logout").permitAll()
//                .anyRequest().authenticated();
//        http.logout();
//        控制单个用户只能创建一个session(实现同一时间只能单一帐号登录的限制)
//        http.sessionManagement().maximumSessions(1).expiredUrl("/login");
//        Session过期后跳转界面
//        http.sessionManagement().invalidSessionUrl("/login");
//        super.configure(http);


    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(myPasswordEncoder());
    }

    @Bean
    public PasswordEncoder myPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
