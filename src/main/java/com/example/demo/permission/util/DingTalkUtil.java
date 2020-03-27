package com.example.demo.permission.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alin
 */

@Component
public class DingTalkUtil {

    /**
     * 获取钉钉accessToken
     *
     * @return accessToken
     */
    public String getDingTalkAccessToken(String appKey, String appSecret) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest req = new OapiGettokenRequest();
            req.setAppkey(appKey);
            req.setAppsecret(appSecret);
            req.setHttpMethod("GET");
            OapiGettokenResponse rsp = client.execute(req);
            return rsp.getAccessToken();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取部门列表
     *
     * @return
     */
    public OapiDepartmentListResponse getDingTalkDeptListDepartmentListResponse(String dingTalkAccessToken, String id) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
            OapiDepartmentListRequest req = new OapiDepartmentListRequest();
            req.setFetchChild(true);
            req.setId(id);
            req.setHttpMethod("GET");
            OapiDepartmentListResponse rsp = client.execute(req, dingTalkAccessToken);
            return rsp;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查找子部门列表
     *
     * @param dingTalkAccessToken accessToken
     * @param parentId            parentId
     */
    public OapiDepartmentListIdsResponse searchListIds(String dingTalkAccessToken, String parentId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_ids");
            OapiDepartmentListIdsRequest req = new OapiDepartmentListIdsRequest();
            req.setId(parentId);
            req.setHttpMethod("GET");
            OapiDepartmentListIdsResponse rsp = client.execute(req, dingTalkAccessToken);
            return rsp;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 创建部门
     *
     * @param dingTalkAccessToken accessToken
     * @param name                部门名称
     * @param parentId            父ID
     * @param order               排序
     * @param createDeptGroup     是不是创建部门群
     * @return msg
     */
    public String createDept(String dingTalkAccessToken, String name, String parentId, String order, String createDeptGroup) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/create");
            OapiDepartmentCreateRequest req = new OapiDepartmentCreateRequest();
            req.setName(name);
            req.setParentid(parentId);
            req.setOrder(order);
            req.setCreateDeptGroup(Boolean.valueOf(createDeptGroup));
            OapiDepartmentCreateResponse rsp = client.execute(req, dingTalkAccessToken);
            return rsp.getBody();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除部门
     *
     * @param dingTalkAccessToken accessToken
     * @param id                  id
     * @return msg
     */
    public OapiDepartmentDeleteResponse deleteDept(String dingTalkAccessToken, String id) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/delete");
            OapiDepartmentDeleteRequest req = new OapiDepartmentDeleteRequest();
            req.setId(id);
            req.setHttpMethod("GET");
            OapiDepartmentDeleteResponse rsp = client.execute(req, dingTalkAccessToken);
            return rsp;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新部门
     *
     * @param dingTalkAccessToken accessToken
     * @param deptId              部门ID
     * @param deptName            部门名称
     * @param createDeptGroup     创建群？
     * @param autoJoinGroup       用户自动加入
     * @return msg
     */
    public OapiDepartmentUpdateResponse updateDept(String dingTalkAccessToken, String deptId, String deptName, String createDeptGroup, String autoJoinGroup) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/update");
            OapiDepartmentUpdateRequest req = new OapiDepartmentUpdateRequest();
            req.setId(Long.valueOf(deptId));
            req.setAutoAddUser(Boolean.valueOf(autoJoinGroup));
            req.setCreateDeptGroup(Boolean.valueOf(createDeptGroup));
            req.setName(deptName);
            OapiDepartmentUpdateResponse rsp = client.execute(req, dingTalkAccessToken);
            return rsp;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 获取部门用户
     *
     * @param dingTalkAccessToken accessToken
     * @param deptId              部门ID
     * @return msg
     */
    public OapiUserSimplelistResponse simpleList(String dingTalkAccessToken, String deptId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
            OapiUserSimplelistRequest req = new OapiUserSimplelistRequest();
            req.setDepartmentId(Long.valueOf(deptId));
            req.setHttpMethod("GET");
            OapiUserSimplelistResponse rsp = client.execute(req, dingTalkAccessToken);
            return rsp;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建用户
     *
     * @param dingTalkAccessToken accessToken
     * @param deptId              部门id
     * @param name                name
     * @param mobile              手机号
     * @return msg
     */
    public OapiUserCreateResponse createUser(String dingTalkAccessToken, String deptId, String name, String mobile) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/create");
            OapiUserCreateRequest req = new OapiUserCreateRequest();
            req.setMobile(mobile);
            req.setName(name);
            // 需要用字符串， "[100,200]" 这种格式
            List<Long> departments = new ArrayList<Long>();
            departments.add(Long.valueOf(deptId));
            req.setDepartment(String.valueOf(departments));
            OapiUserCreateResponse rsp = client.execute(req, dingTalkAccessToken);
            return rsp;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除用户
     *
     * @param dingTalkAccessToken accessToken
     * @param userId              用户ID
     * @return msg
     */
    public OapiUserDeleteResponse deleteUser(String dingTalkAccessToken, String userId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/delete");
            OapiUserDeleteRequest req = new OapiUserDeleteRequest();
            req.setUserid(userId);
            req.setHttpMethod("GET");
            OapiUserDeleteResponse rsp = client.execute(req, dingTalkAccessToken);
            return rsp;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OapiUserUpdateResponse updateUser(String dingTalkAccessToken, String userId, String userName) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/update");
            OapiUserUpdateRequest req = new OapiUserUpdateRequest();
            req.setUserid(userId);
            req.setName(userName);
            OapiUserUpdateResponse rsp = client.execute(req, dingTalkAccessToken);
            return rsp;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
