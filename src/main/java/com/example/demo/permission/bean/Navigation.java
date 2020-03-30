package com.example.demo.permission.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author alin
 */
@Getter
@Setter
public class Navigation implements Serializable {
    private Integer id;

    private String name;

    private Integer pid;

    private String pname;

    private String descpt;

    private String url;

    private String create_time;

    private String update_time;

    private Integer delFlag;

    List<Navigation> childrens;

    private String roles;
}
