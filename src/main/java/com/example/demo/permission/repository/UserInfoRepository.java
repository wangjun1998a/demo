package com.example.demo.permission.repository;

import com.example.demo.permission.bean.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 和数据库交互
 *
 * @author alin
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    /**
     * 获取数据方法 SpringData 会自动生成sql语句
     *
     * @param username 用户名
     * @return null
     */
    UserInfo findByUsername(String username);

}
