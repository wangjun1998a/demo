package com.example.demo.permission.service;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * AOP日志操作
 *
 * @author alin
 */
public interface LoggerService {
    /**
     * 插入日志数据
     *
     * @param ip          ip
     * @param operator    用户名
     * @param remark      备注信息
     * @param role        规则
     * @param description 关联关系
     */
    void insertLogger(@Param("ip") String ip, @Param("operator") String operator,
                      @Param("remark") String remark, @Param("role") String role,
                      @Param("description") String description);
}
