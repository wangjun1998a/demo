package com.example.demo.permission.controller;

import com.example.demo.permission.annotation.LoggerOperator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author alin
 */
@Controller
public class HomeController {
    @LoggerOperator(description = "登录")
    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @LoggerOperator(description = "主菜单")
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "/index";
    }


}

