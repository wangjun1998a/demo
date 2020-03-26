package com.example.demo.permission.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Component;

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

}
