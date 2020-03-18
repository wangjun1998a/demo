package com.example.demo.permission.controller;

import com.example.demo.permission.annotation.LoggerOperator;
import com.example.demo.permission.service.UserOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author alin
 */
@Controller
@RequestMapping(value = "/userOperation", method = RequestMethod.POST)
public class UserOperationController {
    @Autowired
    private UserOperationService userOperationService;

    /**
     * 修改密码
     *
     * @param oldPwd 原密码
     * @param newPwd 新密码
     */

    @LoggerOperator(description = "修改密码")
    @GetMapping("/modifyPwd")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")
    public String modifyPwd(String oldPwd, String newPwd) {
        return userOperationService.modifyPwd(oldPwd, newPwd);
    }
}
