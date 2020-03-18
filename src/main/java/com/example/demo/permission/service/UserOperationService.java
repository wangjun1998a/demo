package com.example.demo.permission.service;

import org.apache.ibatis.annotations.Param;

/**
 * @author alin
 */
public interface UserOperationService {
    /**
     * 修改密码
     *
     * @param oldPwd    原密码
     * @param newPwd    新密码
     * @return modifyPwd
     */

    String modifyPwd(@Param("oldPwd") String oldPwd, @Param("newPwd") String newPwd);
}
