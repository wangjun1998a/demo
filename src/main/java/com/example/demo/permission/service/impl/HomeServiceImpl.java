package com.example.demo.permission.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.example.demo.permission.repository.UserInfoRepository;
import com.example.demo.permission.service.HomeService;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author alin
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${dingTalk.accessKey}")
    private String accessKey;

    @Value("${dingTalk.accessSecret}")
    private String accessSecret;

    @Override
    public OapiSnsGetuserinfoBycodeResponse getUserByCode(String code, String state) {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
        OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
        req.setTmpAuthCode(code);
        try {
            OapiSnsGetuserinfoBycodeResponse response = client.execute(req, accessKey, accessSecret);
            String openid = response.getUserInfo().getOpenid();
            String displayName = response.getUserInfo().getNick();
            String s = searchIfExists(openid);
            if ("0".equals(s)) {
                createUser(openid, displayName);
                String newUserId = searchNewUser(openid);
                if (newUserId != null) {
                    insertUserRole(newUserId);
                }
            }
            return response;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void insertUserRole(String newUserId) {
        userInfoRepository.insertUserRole(newUserId);
    }

    private String searchNewUser(String openid) {
        return userInfoRepository.searchNewUser(openid);
    }

    public void createUser(String code, String displayName) {
        userInfoRepository.createUser(code, passwordEncoder.encode(code), displayName);
    }

    public String searchIfExists(String code) {
        return userInfoRepository.searchIfExists(code);
    }
}
