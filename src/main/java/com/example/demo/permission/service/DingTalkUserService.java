package com.example.demo.permission.service;

public interface DingTalkUserService {
    /**
     * 获取部门用户
     *
     * @param deptId 部门ID
     * @return msg
     */
    String simpleList(String deptId);

    /**
     * 创建用户
     *
     * @param deptId 部门ID
     * @param name   用户姓名
     * @param mobile 手机号码
     * @return msg
     */
    String create(String deptId, String name, String mobile);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return msg
     */
    String delete(String userId);

    /**
     * 更新用户
     *
     * @param userId   用户ID
     * @param userName 用户名
     * @return msg
     */
    String update(String userId, String userName);
}
