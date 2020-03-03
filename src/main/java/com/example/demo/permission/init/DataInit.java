package com.example.demo.permission.init;

import com.example.demo.permission.bean.UserInfo;
import com.example.demo.permission.repository.UserInfoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author alin
 */
@Service
public class DataInit {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

	public DataInit(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
		this.userInfoRepository = userInfoRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostConstruct
    public void dataInit() {

        UserInfo admin = new UserInfo();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setRole(UserInfo.Role.admin);
        userInfoRepository.save(admin);


        UserInfo user = new UserInfo();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(UserInfo.Role.normal);
        userInfoRepository.save(user);
    }

}
