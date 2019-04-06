package com.github.abigail830.timeticket.domain.wx;

import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Setter
@Getter
@Slf4j
public class WxDomain {

    private String appId;
    private String appSecret;
    private WxCommonInterface wxCommonInterface;

    private WxLoginInfo wxLoginInfo;


    public WxDomain(String appId, String appSecret, WxCommonInterface wxCommonInterface) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.wxCommonInterface = wxCommonInterface;
    }

    public WxLoginInfo login(String headerCode) {
        try {
            wxLoginInfo = wxCommonInterface.login(appId, appSecret, headerCode);
            return wxLoginInfo;

        } catch (JsonSyntaxException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "False to parse the result to json from WeChat backend.");

        } catch (RuntimeException runtimeEx) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "Exception when http login WeChat backend");
        }
    }

    public boolean isLoginSuccess() {
        if (wxLoginInfo.getOpenid() != null)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

}
