package com.github.abigail830.timeticket.infrastructure.client;

import com.github.abigail830.timeticket.domain.user.User;
import com.github.abigail830.timeticket.infrastructure.InfraException;
import com.github.abigail830.timeticket.util.http.HttpClientUtil;
import com.github.abigail830.timeticket.util.http.JsonUtil;
import com.google.gson.JsonSyntaxException;
import org.springframework.stereotype.Component;

@Component
public class WxClientImpl implements WxClient {

    private static final String LOGIN_URL =
            "https://api.weixin.qq.com/sns/jscode2session?appid=APP_ID" +
                    "&secret=APP_SECRET" +
                    "&grant_type=authorization_code" +
                    "&js_code=HEADER_CODE";

    @Override
    public User login(String appId, String appSecret, String headerCode) {
        final String loginUrl = LOGIN_URL.replace("APP_ID", appId)
                .replace("APP_SECRET", appSecret)
                .replace("HEADER_CODE", headerCode);

        WxLoginResponse wxLoginResponse = getWxLoginResponse(loginUrl);
        if (wxLoginResponse != null && wxLoginResponse.isValidResponse())
            return wxLoginResponse.toUser();
        else
            throw new InfraException("Fail to login WxChet Backend");

    }

    private WxLoginResponse getWxLoginResponse(String loginUrl) {

        WxLoginResponse wxLoginResponse;
        try {
            final String result = HttpClientUtil.instance().getData(loginUrl);
            wxLoginResponse = JsonUtil.toObject(result, WxLoginResponse.class);

        } catch (JsonSyntaxException ex) {
            throw new InfraException("False to parse the result to json from WeChat backend.");

        } catch (RuntimeException runtimeEx) {
            throw new InfraException("Exception when http login WeChat backend");
        }
        return wxLoginResponse;
    }
}
