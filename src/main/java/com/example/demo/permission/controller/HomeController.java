package com.example.demo.permission.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author alin
 */
@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping({"", "/", "/index"})
    public String index() {
        return "/index";
    }


}

