package com.example.demo.permission.service.impl;

import com.example.demo.permission.repository.LoggerRepository;
import com.example.demo.permission.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author alin
 */
@Service
public class LoggerServiceImpl implements LoggerService {

    @Autowired
    private LoggerRepository loggerRepository;

    /**
     * 插入日志数据
     *
     * @param params Map
     */
    @Override
    public void insertLogger(Map<String, String> params) {
        loggerRepository.insertLogger(params);
    }
}
