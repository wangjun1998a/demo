package com.example.demo.permission.service;

import java.util.Map;

/**
 * @author alin
 */
public interface NavigationService {
    /**
     * 查找菜单
     *
     * @return 菜单Map
     */
    Map<String, Object> findMenu();

    /**
     * 获取数据表
     *
     * @return 菜单表
     */
    Map<String, Object> findMenuTable();

    /**
     * 插入数据函数
     *
     * @param name   name
     * @param pid    pid
     * @param descpt descpt
     * @param url    url
     */
    String insertData(String name, Integer pid, String descpt, String url);
}
