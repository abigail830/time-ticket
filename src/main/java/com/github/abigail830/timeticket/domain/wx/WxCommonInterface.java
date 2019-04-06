package com.github.abigail830.timeticket.domain.wx;

public interface WxCommonInterface {

    WxLoginInfo login(String appId, String appSecret, String headerCode);
}
