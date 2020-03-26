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
     * @param dingTalkId 钉钉ID
     * @return Map
     */
    String getTableDept(String dingTalkId);

    /**
     * 创建部门
     *
     * @param name            name
     * @param parentId        parentId
     * @param createDeptGroup createDeptGroup
     * @return json
     */
    String createDept(String name, String parentId, String createDeptGroup);

    /**
     * 删除部门
     *
     * @param id 部门ID
     * @return msg
     */
    String deleteDept(String id);

    /**
     * 更新部门
     *
     * @param deptId id
     * @param deptName
     * @param createDeptGroup
     * @param autoJoinGroup
     * @return
     */
    String updateDept(String deptId, String deptName, String createDeptGroup, String autoJoinGroup);
}
