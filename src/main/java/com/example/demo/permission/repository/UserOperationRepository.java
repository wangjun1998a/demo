package com.example.demo.permission.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author alin
 */
@Mapper
@Repository
public interface UserOperationRepository {
    /**
     * 修改密码
     *
     * @param userName 用户名
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

    /**
     * 更新数据库用户图片的字段
     *
     * @param userName 用户名
     * @param picPath  图片路径
     */
    @Update("update `spring-security`.user_info set picture = #{picPath} where username = #{userName}")
    void updateUserPictures(@Param("userName") String userName, @Param("picPath") String picPath);

    /**
     * 查询用户的头像路径
     *
     * @param userName 用户名
     * @return 头像路径
     */
    @Select("select picture from `spring-security`.user_info ui where ui.username = #{userName};")
    String getHeadImagePath(@Param("userName") String userName);

    /**
     * 获取显示名称
     *
     * @param userName 用户名
     * @return 显示名称
     */
    @Select("select displayname from `spring-security`.user_info ui where ui.username = #{userName};")
    String getShowUserName(@Param("userName") String userName);
}
