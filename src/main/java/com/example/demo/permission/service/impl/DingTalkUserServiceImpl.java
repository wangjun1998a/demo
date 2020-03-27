package com.example.demo.permission.service.impl;

import com.dingtalk.api.response.OapiUserCreateResponse;
import com.dingtalk.api.response.OapiUserDeleteResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.dingtalk.api.response.OapiUserUpdateResponse;
import com.example.demo.permission.service.DingTalkUserService;
import com.example.demo.permission.util.DingTalkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author alin
 */
@Service
public class DingTalkUserServiceImpl implements DingTalkUserService {

    @Value("${dingTalk.appkey}")
    private String appKey;

    @Value("${dingTalk.appsecret}")
    private String appSecret;

    @Autowired
    private DingTalkUtil dingTalkUtil;

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return msg
     */
    @Override
    public String delete(String userId) {
        String dingTalkAccessToken = dingTalkUtil.getDingTalkAccessToken(appKey, appSecret);
        OapiUserDeleteResponse oapiUserDeleteResponse = dingTalkUtil.deleteUser(dingTalkAccessToken, userId);
        return oapiUserDeleteResponse.getBody();
    }

    /**
     * 更新用户
     *
     * @param userId   用户ID
     * @param userName 用户名
     * @return msg
     */
    @Override
    public String update(String userId, String userName) {
        String dingTalkAccessToken = dingTalkUtil.getDingTalkAccessToken(appKey, appSecret);
        OapiUserUpdateResponse oapiUserUpdateResponse = dingTalkUtil.updateUser(dingTalkAccessToken, userId, userName);
        return oapiUserUpdateResponse.getBody();
    }

    /**
     * 创建用户
     *
     * @param deptId 部门ID
     * @param name   用户姓名
     * @param mobile 手机号码
     * @return msg
     */
    @Override
    public String create(String deptId, String name, String mobile) {
        String dingTalkAccessToken = dingTalkUtil.getDingTalkAccessToken(appKey, appSecret);
        OapiUserCreateResponse oapiUserCreateResponse = dingTalkUtil.createUser(dingTalkAccessToken, deptId, name, mobile);
        return oapiUserCreateResponse.getBody();
    }

    /**
     * 获取部门用户
     *
     * @param deptId 部门ID
     * @return msg
     */
    @Override
    public String simpleList(String deptId) {
        String dingTalkAccessToken = dingTalkUtil.getDingTalkAccessToken(appKey, appSecret);
        OapiUserSimplelistResponse oapiUserSimplelistResponse = dingTalkUtil.simpleList(dingTalkAccessToken, deptId);
        return oapiUserSimplelistResponse.getBody()
                .replaceAll("errcode", "code")
                .replaceAll("userlist", "data");
    }

}
