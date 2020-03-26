package com.example.demo.permission.service;

import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;

/**
 * @author alin
 */
public interface HomeService {

    /**
     * DingTalk调用
     *
     * @param code  code
     * @param state 状态
     * @return OapiSnsGetuserinfoBycodeResponse
     */
    OapiSnsGetuserinfoBycodeResponse getUserByCode(String code, String state);

}
