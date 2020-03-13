package com.example.demo.permission.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
 * 日志存取位置
 *
 * @author alin
 */
@Mapper
public interface LoggerRepository {
    /**
     * 日志插入的具体实现
     *
     * @param ip          IP
     * @param operator    操作人
     * @param remark      备注
     * @param role        规则
     * @param description 关联关系
     */

    @Insert("insert into `spring-security`.logger(ip, operator, remark, role,createTime, description)\n" +
            "values (#{ip},#{operator},#{remark},#{role},now(),#{description});")
    void insertLogger(String ip, String operator, String remark, String role, String description);
}
