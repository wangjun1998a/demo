package com.example.demo.permission.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author alin
 * 在roles里面添加了规则以后，在方法处注解@PreAuthorize("hasAnyRole('指定的用户名')")就可以指定用户访问方法了。
 */
@RestController
@RequestMapping("/hello")
public class IndexController {
    @GetMapping
    public String index() {
        return "Hello Spring Security!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('admin')")
    public String helloAdmin(

    ) {
        return "Hello admin!";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('admin','normal')")
    public String helloUser() {
        return "Hello user!";
    }
}
