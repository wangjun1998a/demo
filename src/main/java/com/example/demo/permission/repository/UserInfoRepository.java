package com.example.demo.permission.repository;

import com.example.demo.permission.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;


/**
 * 和数据库交互
 *
 * @author alin
 */
@Mapper
public interface UserInfoRepository {

    /**
     * 获取数据方法 SpringData 会自动生成sql语句
     *
     * @param username 用户名
     * @return null
     */
//    @Select("select * from `spring-security`.user_info where username = #{username};")
    UserInfo findByUsername(String username);

    /**
     * 新增数据
     *
     * @param userInfo userInfo
     */
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    void insertData(UserInfo userInfo);
}
