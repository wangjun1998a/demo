package com.example.demo.permission.service;

import java.util.Map;

/**
 * @author alin
 */
public interface DepartmentService {
    /**
     * 获取部门列表
     *
     * @return 部门列表
     */
    Map<String, Object> getDept();

    /**
     * 获取部门的表格数据
     *
     * @return Map
     */
    String getTableDept(String dingTalkId);


}
