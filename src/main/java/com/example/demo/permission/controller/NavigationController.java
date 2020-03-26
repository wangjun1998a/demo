package com.example.demo.permission.controller;


import com.example.demo.permission.annotation.LoggerOperator;
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

    @LoggerOperator(description = "查找菜单")
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
    @LoggerOperator(description = "插入菜单")
    @GetMapping("/insertMenu")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin')")
    public String insertMenu(String name, Integer pid, String descpt, String url) {
        navigationService.insertMenu(name, pid, descpt, url);
        return "200";
    }

    @LoggerOperator(description = "删除菜单")
    @GetMapping("/deleteMenu")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin')")
    public String deleteMenu(String id) {
        navigationService.deleteMenu(id);
        return "200";
    }

}
