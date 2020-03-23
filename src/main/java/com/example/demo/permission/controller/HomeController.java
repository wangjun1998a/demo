package com.example.demo.permission.controller;

import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.example.demo.permission.annotation.LoggerOperator;
import com.example.demo.permission.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author alin
 */
@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;

    @LoggerOperator(description = "登录")
    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @LoggerOperator(description = "参数跳转页面")
    @GetMapping({"/", "/authentication"})
    public String authentication() {
        return "/authentication";
    }

//    @LoggerOperator(description = "创建用户")
//    @GetMapping({"/createUser"})
//    @ResponseBody
//    public String createUser(String code, String state) {
//        String result = "500";
//
//        return result;
//    }

    @LoggerOperator(description = "获取扫码验证信息")
    @GetMapping({"/getCodeMsg"})
    @ResponseBody
    public String getCodeMsg(String code, String state) {
        if (state != null) {
            OapiSnsGetuserinfoBycodeResponse userByCode = homeService.getUserByCode(code, state);

            return userByCode.getBody();
        }
        return null;
    }

    @LoggerOperator(description = "主菜单")
    @GetMapping({"/index"})
    public String index() {
        return "/index";
    }


}

