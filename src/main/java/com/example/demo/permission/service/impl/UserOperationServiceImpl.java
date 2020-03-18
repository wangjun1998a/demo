package com.example.demo.permission.service.impl;

import com.example.demo.permission.repository.UserOperationRepository;
import com.example.demo.permission.service.UserOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author alin
 */
@Service
public class UserOperationServiceImpl implements UserOperationService {

    @Autowired
    private UserOperationRepository userOperationRepository;

    public PasswordEncoder myPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 修改密码
     *
     * @param oldPwd 原密码
     * @param newPwd 新密码
     * @return modifyPwd
     */

    @Override
    public String modifyPwd(String oldPwd, String newPwd) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
//        从数据库中获取加密的密码。
        String passwordInDataBase = userOperationRepository.getPasswdWithUserName(userName);
        boolean matches = myPasswordEncoder().matches(oldPwd, passwordInDataBase);
        if (matches) {
            userOperationRepository.modifyPwd(userName, oldPwd, myPasswordEncoder().encode(newPwd));
            return "200";
        }
        return "500";
    }
}
