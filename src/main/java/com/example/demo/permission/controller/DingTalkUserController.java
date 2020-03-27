package com.example.demo.permission.controller;

import com.example.demo.permission.annotation.LoggerOperator;
import com.example.demo.permission.service.DingTalkUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author alin
 */
@Controller
@RequestMapping(value = "/user")
public class DingTalkUserController {

    @Autowired
    private DingTalkUserService dingTalkUserService;

    @LoggerOperator(description = "获取部门用户")
    @GetMapping({"/simpleList"})
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")
    public String simpleList(String deptId) {
        return dingTalkUserService.simpleList(deptId);
    }

    @LoggerOperator(description = "创建用户")
    @GetMapping({"/create"})
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")
    public String create(String deptId, String name, String mobile) {
        return dingTalkUserService.create(deptId, name, mobile);
    }


    @LoggerOperator(description = "删除用户")
    @GetMapping({"/delete"})
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")
    public String delete(String userId) {
        return dingTalkUserService.delete(userId);
    }

    @LoggerOperator(description = "更新用户")
    @GetMapping({"/update"})
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")
    public String update(String userId, String userName) {
        return dingTalkUserService.update(userId, userName);
    }


}
