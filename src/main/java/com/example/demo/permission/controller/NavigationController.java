package com.example.demo.permission.controller;


import com.example.demo.permission.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author alin
 */
@Controller
@RequestMapping("/getData")
public class NavigationController {
    @Autowired
    NavigationService navigationService;

    @GetMapping("/findMenu")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")

    public Map<String, Object> findMenu() {
        Map<String, Object> data = navigationService.findMenu();
        return data;
    }
}
