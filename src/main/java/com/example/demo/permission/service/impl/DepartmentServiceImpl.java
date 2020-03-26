package com.example.demo.permission.service.impl;

import com.dingtalk.api.response.OapiDepartmentListResponse;
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
        OapiDepartmentListResponse dingTalkDeptList = dingTalkUtil.getDingTalkDeptListDepartmentListResponse(dingTalkAccessToken, "");
        List<OapiDepartmentListResponse.Department> department = dingTalkDeptList.getDepartment();
        Map<String, Object> returnData = getReturnData(department);
        return returnData;
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
     * 获取仿佛的Json
     *
     * @param department dingTalk返回的值
     * @return
     */
    private Map<String, Object> getReturnData(List<OapiDepartmentListResponse.Department> department) {
        Map<String, Object> allData = new HashMap<>();
        for (int i = 0; i < department.size(); i++) {
            Long parentId = department.get(i).getParentid();
//            如果没有父ID就是顶级
            if (parentId == null) {
                List list = new ArrayList();
                Map<String, Object> data = new HashMap<>();
//                List list1 = new ArrayList();
                String id = String.valueOf(department.get(i).getId());
                String name = department.get(i).getName();
                data.put("title", name);
                data.put("id", id);
                data.put("spread", "true");
//              删除已经保存的记录
                department.remove(i);
                i -= 1;
                List childrenData = getChildrenData(department, id);
                data.put("children", childrenData);
                list.add(data);
                allData.put("data", list);
            }
        }
        return allData;
    }

    private List getChildrenData(List<OapiDepartmentListResponse.Department> department, String parentId) {
        List list = new ArrayList();
        for (int i = 0; i < department.size(); i++) {
            String pid = String.valueOf(department.get(i).getParentid());
            if (parentId.equals(pid)) {
                Map<String, Object> data = new HashMap<>();
                String id = String.valueOf(department.get(i).getId());
                String name = department.get(i).getName();
                data.put("title", name);
                data.put("id", id);
                data.put("spread", "true");
//              删除已经保存的记录
                department.remove(i);
                i = 0;
                List childrenData = getChildrenData(department, id);
                if (childrenData.size() != 0) {
                    data.put("children", childrenData);
                }
                list.add(data);
                i -= 1;
            }

        }
        return list;

    }

}
