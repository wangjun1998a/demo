package com.example.demo.permission.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author alin
 */
@Getter
@Setter
public class MenuTable implements Serializable {

    /**
     * id : 1
     * name : 默认打开
     * pid : 0
     * descpt : 默认打开
     * url :
     * create_time : 2020-3-5 16:10:10
     * update_time : 2020-3-5 16:10:10
     * del_flag :
     */

    private Integer id;
    private String name;
    private Integer pid;
    private String descpt;
    private String url;
}
