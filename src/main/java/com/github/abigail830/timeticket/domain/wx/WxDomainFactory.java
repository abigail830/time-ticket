package com.github.abigail830.timeticket.domain.wx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WxDomainFactory {

    @Autowired
    WxCommonInterface wxCommonInterface;
    @Value("${app.appId}")
    private String appId;
    @Value("${app.appSecret}")
    private String appSecret;

    public WxDomain build() {
        return new WxDomain(appId, appSecret, wxCommonInterface);
    }


}
