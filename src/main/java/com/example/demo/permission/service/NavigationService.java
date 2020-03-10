package com.example.demo.permission.service;

import org.apache.ibatis.annotations.Param;

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
     * @return 200
     */
    String insertMenu(@Param("name") String name, @Param("pid") Integer pid, @Param("descpt") String descpt, @Param("url") String url);

    /**
     *  删除菜单数据
     * @param id 菜单ID
     * @return  200
     */
    String deleteMenu(@Param("id") String id);
}
