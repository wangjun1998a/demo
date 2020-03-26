package com.example.demo.permission.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author alin
 */
public interface UserOperationService {
    /**
     * 修改密码
     *
     * @param oldPwd 原密码
     * @param newPwd 新密码
     * @return modifyPwd
     */

    String modifyPwd(@Param("oldPwd") String oldPwd, @Param("newPwd") String newPwd);

    /**
     * 修改用户头像
     *
     * @param file    文件名
     * @param request http
     * @return map
     */
    Map modifyHeadPortrait(MultipartFile file, HttpServletRequest request);

    /**
     * 获取用户头像
     *
     * @return img
     */
    Map<String, Object> getHeadImagePath();
}
