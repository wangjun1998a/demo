package com.example.demo.permission.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author alin
 */
@Mapper
public interface UserOperationRepository {
    /**
     * 修改密码
     *
     * @param userName
     * @param oldPwd   原密码
     * @param newPwd   新密码
     */

    @Update("update `spring-security`.user_info ui set password = #{newPwd} where username = #{userName} ")
    void modifyPwd(@Param("userName") String userName, @Param("oldPwd") String oldPwd, @Param("newPwd") String newPwd);

    /**
     * 获取数据库的存储的密码字段
     *
     * @param userName userName
     * @return 加密的密码。
     */
    @Select("select password from `spring-security`.user_info ui where username = #{userName};")
    String getPasswdWithUserName(@Param("userName") String userName);
}
