package com.example.demo.permission.service.impl;

import com.example.demo.permission.repository.LoggerRepository;
import com.example.demo.permission.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author alin
 */
@Service
public class LoggerServiceImpl implements LoggerService {

    @Autowired
    private LoggerRepository loggerRepository;


    @Override
    public void insertLogger(String ip, String operator,
                             String remark, String role,
                             String description) {
        loggerRepository.insertLogger(ip, operator, remark, role, description);
    }
}
