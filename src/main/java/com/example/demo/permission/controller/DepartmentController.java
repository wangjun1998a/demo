package com.example.demo.permission.controller;

import com.example.demo.permission.annotation.LoggerOperator;
import com.example.demo.permission.service.DepartmentService;
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
@RequestMapping(value = "/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @LoggerOperator(description = "获取部门列表")
    @GetMapping({"/getDept"})
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")
    public Map<String, Object> getDept() {
        return departmentService.getDept();
    }

    @LoggerOperator(description = "获取部门的表格数据")
    @GetMapping({"/getTableDept"})
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin','normal')")
    public String getTableDept(String dingTalkId) {
        return departmentService.getTableDept(dingTalkId);
    }

    @LoggerOperator(description = "dingTalk创建部门")
    @GetMapping("/createDept")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin')")
    public String createDept(String name, String parentId, String createDeptGroup) {
        return departmentService.createDept(name, parentId, createDeptGroup);
    }

    @LoggerOperator(description = "dingTalk删除部门")
    @GetMapping("/deleteDept")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin')")
    public String deleteDept(String id) {
        return departmentService.deleteDept(id);
    }

    @LoggerOperator(description = "dingTalk更新部门")
    @GetMapping("/updateDept")
    @ResponseBody
    @PreAuthorize("hasAnyRole('admin')")
    public String updateDept(String deptId, String deptName, String createDeptGroup, String autoJoinGroup) {
        return departmentService.updateDept(deptId , deptName , createDeptGroup , autoJoinGroup);
    }
}