package com.example.demo.permission.service;

import java.util.Map;

/**
 * AOP日志操作
 *
 * @author alin
 */
public interface LoggerService {
    /**
     * 插入日志数据
     *
     * @param params Map
     */
    void insertLogger(Map<String, String> params);
}
