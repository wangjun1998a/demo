package com.example.demo.permission.service.impl;

import com.example.demo.permission.bean.UserInfo;
import com.example.demo.permission.repository.UserInfoRepository;
import com.example.demo.permission.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author alin
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

	private final UserInfoRepository userInfoRepository;

	public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
		this.userInfoRepository = userInfoRepository;
	}

	@Override
	public UserInfo findByUsername(String username) {
		return userInfoRepository.findByUsername(username);
	}

}
