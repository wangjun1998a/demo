package com.example.demo.permission.repository;

import com.example.demo.permission.bean.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * 和数据库交互
 *
 * @author alin
 */
@Mapper
@Repository
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
    UserInfo findByUsername(@Param("username") String username);

    /**
     * 新增数据
     *
     * @param username    用户名
     * @param password    密码
     * @param displayName 显示名称
     */
    @Insert("insert into `spring-security`.user_info " +
            "(password, role, username,displayname) values " +
            "(#{password},'2',#{username},#{displayName}) ;")
    void createUser(@Param("username") String username, @Param("password") String password,
                    @Param("displayName") String displayName);

    /**
     * 查询是不是存在 是返回1 不是返回0
     *
     * @param code code
     * @return 1/0
     */
    @Select("select count(*) from `spring-security`.user_info where username = #{code};")
    String searchIfExists(@Param("code") String code);

    /**
     * 查询新插入的用户ID
     *
     * @param openid 其实就是用户名
     * @return 用户ID
     */
    @Select("select uid from `spring-security`.user_info where username = #{openid};")
    String searchNewUser(@Param("openid") String openid);

    /**
     * 插入权限数据 默认是2 normal
     *
     * @param newUserId 用户ID
     */
    @Insert("insert into `spring-security`.user_role (user_id, role_id) values (#{newUserId},'2');")
    void insertUserRole(@Param("newUserId") String newUserId);
}
