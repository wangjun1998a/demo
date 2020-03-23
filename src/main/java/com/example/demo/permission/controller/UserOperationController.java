package com.example.demo.permission.controller;

import com.example.demo.permission.annotation.LoggerOperator;
import com.example.demo.permission.service.UserOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "用户操作接口")
@Controller
@RequestMapping(value = "/userOperation", method = RequestMethod.POST)
public class UserOperationController {
    @Autowired
    private UserOperationService userOperationService;

    @ApiOperation(value = "modifyPwd - 修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPwd", value = "原密码", required = false, paramType = "query"),
            @ApiImplicitParam(name = "newPwd", value = "新密码", required = false, paramType = "query")
    })
    @LoggerOperator(description = "修改密码")
    @GetMapping("/modifyPwd")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")
    public String modifyPwd(String oldPwd, String newPwd) {
        return userOperationService.modifyPwd(oldPwd, newPwd);
    }

    @ApiOperation(value = "modifyHeadPortrait - 修改头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件路径", required = false, paramType = "query"),
            @ApiImplicitParam(name = "request", value = "HttpServletRequest", required = false, paramType = "query")
    })
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
