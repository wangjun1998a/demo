package com.example.demo.permission.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Logger {

    private Integer id;
    private String ip;
    String operator;
    String remark;
    String role;
    Date createTime;
    String description;
}
