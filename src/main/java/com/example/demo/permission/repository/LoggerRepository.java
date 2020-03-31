package com.example.demo.permission.repository;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 日志存取位置
 *
 * @author alin
 */
@Repository
public interface LoggerRepository {
    /**
     * Map的方式传入参数
     *
     * @param params Map
     */
    @Insert({"insert into `spring-security`.logger (ip, operator, remark, role,createTime, description)\n" +
            "values (#{ip},#{userRole},#{method},#{role},now(),#{desc});"})
    void insertLogger(Map<String, String> params);
}
