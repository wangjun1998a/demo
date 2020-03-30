package com.example.demo.permission.service.impl;

import com.dingtalk.api.response.OapiDepartmentDeleteResponse;
import com.dingtalk.api.response.OapiDepartmentListIdsResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentUpdateResponse;
import com.example.demo.permission.bean.Department;
import com.example.demo.permission.service.DepartmentService;
import com.example.demo.permission.util.DingTalkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alin
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

//    @Autowired
//    private DepartmentRepository departmentRepository;

    @Autowired
    private DingTalkUtil dingTalkUtil;


    @Value("${dingTalk.appkey}")
    private String appKey;

    @Value("${dingTalk.appsecret}")
    private String appSecret;


    /**
     * 获取部门列表
     *
     * @return 部门json
     */
    @Override
    public Map<String, Object> getDept() {
        String dingTalkAccessToken = dingTalkUtil.getDingTalkAccessToken(appKey, appSecret);
//        因为需要获取根部门 这里的id不能传入值
        OapiDepartmentListResponse oapiDepartmentListResponse = dingTalkUtil.getDingTalkDeptListDepartmentListResponse(dingTalkAccessToken, "");
        List<OapiDepartmentListResponse.Department> departmentList = oapiDepartmentListResponse.getDepartment();
        return getReturnData(departmentList);
    }

    /**
     * 获取部门的表格数据
     *
     * @return Map
     */
    @Override
    public String getTableDept(String dingTalkId) {
        String dingTalkAccessToken = dingTalkUtil.getDingTalkAccessToken(appKey, appSecret);
        OapiDepartmentListResponse dingTalkDeptList = dingTalkUtil.getDingTalkDeptListDepartmentListResponse(dingTalkAccessToken, dingTalkId);
        return dingTalkDeptList.getBody()
                .replaceAll("errcode", "code")
                .replaceAll("department", "data");
    }

    /**
     * 创建部门
     *
     * @param name            部门名称
     * @param parentId        父ID
     * @param createDeptGroup 是否创建一个关联此部门的企业群
     * @return code
     */
    @Override
    public String createDept(String name, String parentId, String createDeptGroup) {
        String dingTalkAccessToken = dingTalkUtil.getDingTalkAccessToken(appKey, appSecret);

        OapiDepartmentListIdsResponse oapiDepartmentListIdsResponse = dingTalkUtil.searchListIds(dingTalkAccessToken, parentId);
//        设置排序
        String order = String.valueOf(oapiDepartmentListIdsResponse.getSubDeptIdList().size() + 1);
        return dingTalkUtil.createDept(dingTalkAccessToken, name, parentId, order, createDeptGroup);
    }

    /**
     * 删除部门
     *
     * @param id id
     * @return msg
     */
    @Override
    public String deleteDept(String id) {
        String dingTalkAccessToken = dingTalkUtil.getDingTalkAccessToken(appKey, appSecret);
        OapiDepartmentDeleteResponse oapiDepartmentDeleteResponse = dingTalkUtil.deleteDept(dingTalkAccessToken, id);
        return oapiDepartmentDeleteResponse.getBody();
    }

    /**
     * 更新部门
     *
     * @param deptId          id
     * @param deptName        名称
     * @param createDeptGroup 是否创建部门群
     * @param autoJoinGroup   用户是否自动加入
     * @return rsp
     */
    @Override
    public String updateDept(String deptId, String deptName, String createDeptGroup, String autoJoinGroup) {

        String dingTalkAccessToken = dingTalkUtil.getDingTalkAccessToken(appKey, appSecret);
        OapiDepartmentUpdateResponse oapiDepartmentUpdateResponse = dingTalkUtil.updateDept(dingTalkAccessToken, deptId, deptName, createDeptGroup, autoJoinGroup);
        return oapiDepartmentUpdateResponse.getBody();
    }

    /**
     * 获取仿佛的Json
     *
     * @param departmentList dingTalk返回的值
     * @return
     */
    private Map<String, Object> getReturnData(List<OapiDepartmentListResponse.Department> departmentList) {
        Map<String, Object> allData = new HashMap<>();
        List<Department> department = new ArrayList<>();
        for (int i = 0; i < departmentList.size(); i++) {
            Long parentId = departmentList.get(i).getParentid();
//            如果没有父ID就是顶级
            if (parentId == null) {
                Department deptBean = new Department();
                String id = String.valueOf(departmentList.get(i).getId());
                String name = departmentList.get(i).getName();
                deptBean.setId(id);
                deptBean.setTitle(name);
                deptBean.setSpread("true");
                departmentList.remove(i);
                i -= 1;
                deptBean.setChildren(getChildrenData(departmentList, id));
                department.add(deptBean);
            }
        }
        allData.put("data", department);
        return allData;
    }

    private List<Department> getChildrenData(List<OapiDepartmentListResponse.Department> department, String parentId) {
        List<Department> list = new ArrayList();
        for (int i = 0; i < department.size(); i++) {
            String pid = String.valueOf(department.get(i).getParentid());
            if (parentId.equals(pid)) {
                String id = String.valueOf(department.get(i).getId());
                String name = department.get(i).getName();
                Department deptBean = new Department();
                deptBean.setId(id);
                deptBean.setTitle(name);
                deptBean.setSpread("true");
                department.remove(i);
                i = 0;
                deptBean.setChildren(getChildrenData(department, id));
                list.add(deptBean);
            }
        }
        return list;
    }

}
