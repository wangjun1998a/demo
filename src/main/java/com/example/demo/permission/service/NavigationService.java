package com.example.demo.permission.service;

import java.util.Map;

/**
 * @author alin
 */
public interface NavigationService {
    /**
     * 查找菜单
     * @return 菜单Map
     */
    public Map<String, Object> findMenu();
}
