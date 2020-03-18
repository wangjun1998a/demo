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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author alin
 */
@Controller
@RequestMapping(value = "/userOperation", method = RequestMethod.POST)
public class UserOperationController {
    @Autowired
    private UserOperationService userOperationService;

    @LoggerOperator(description = "修改密码")
    @GetMapping("/modifyPwd")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")
    public String modifyPwd(String oldPwd, String newPwd) {
        return userOperationService.modifyPwd(oldPwd, newPwd);
    }

    @LoggerOperator(description = "修改头像")
    @GetMapping("/modifyHeadPortrait")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")
    public Map modifyHeadPortrait(MultipartFile file, HttpServletRequest request) {
        Map map = userOperationService.modifyHeadPortrait(file, request);
        return map;

    }

    @LoggerOperator(description = "获取头像路径")
    @GetMapping("/getHeadImagePath")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")
    public Map<String, Object> getHeadImagePath() {
        Map<String, Object> map = userOperationService.getHeadImagePath();
        return map;
    }


}
