package com.example.demo.permission.service;


import com.example.demo.permission.bean.UserInfo;

/**
 * @author alin
 */
public interface UserInfoService {

	/**
	 * 定义接口
	 * @param username username
	 * @return null
	 */
	UserInfo findByUsername(String username);
	
}
