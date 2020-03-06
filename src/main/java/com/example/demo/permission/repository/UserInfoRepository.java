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
    @Select("select ui.uid as uid, password as password, r.role_key as role, username as username\n" +
            "from `spring-security`.user_info ui\n" +
            "         left join `spring-security`.user_role ur on ur.user_id = ui.uid\n" +
            "         left join `spring-security`.role r on r.role_id = ur.role_id where ui.username = #{username};")
    UserInfo findByUsername(String username);

    /**
     * 新增数据
     *
     * @param userInfo userInfo
     */
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    void insertData(UserInfo userInfo);
}
