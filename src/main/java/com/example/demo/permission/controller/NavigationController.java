package com.example.demo.permission.controller;


import com.example.demo.permission.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author alin
 */
@CrossOrigin("*")
@Controller
@RequestMapping(value = "/getData", method = RequestMethod.POST)
public class NavigationController {
    @Autowired
    NavigationService navigationService;

    @GetMapping("/findMenu")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")

    public Map<String, Object> findMenu() {
        return navigationService.findMenu();
    }

    @GetMapping("/findMenuTable")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")
    public Map<String, Object> findMenuTable() {
        return navigationService.findMenuTable();
    }

    @GetMapping("/insertData")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin')")
    public String insertData(String name, Integer pid, String descpt, String url) {
        navigationService.insertData(name, pid, descpt, url);
        return "200";
    }

}
