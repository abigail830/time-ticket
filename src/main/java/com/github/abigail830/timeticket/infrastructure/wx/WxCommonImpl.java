package com.github.abigail830.timeticket.infrastructure.wx;

import com.github.abigail830.timeticket.domain.wx.WxCommonInterface;
import com.github.abigail830.timeticket.domain.wx.WxLoginInfo;
import com.github.abigail830.timeticket.util.http.HttpClientUtil;
import org.springframework.stereotype.Service;

@Service
public class WxCommonImpl implements WxCommonInterface {

    private static final String LOGIN_URL =
            "https://api.weixin.qq.com/sns/jscode2session?appid=APP_ID" +
                    "&secret=APP_SECRET" +
                    "&grant_type=authorization_code" +
                    "&js_code=HEADER_CODE";

    @Override
    public WxLoginInfo login(String appId, String appSecret, String headerCode) {
        final String loginUrl = LOGIN_URL.replace("APP_ID", appId)
                .replace("APP_SECRET", appSecret)
                .replace("HEADER_CODE", headerCode);
        final String result = HttpClientUtil.instance().getData(loginUrl);
        return WxLoginInfo.fromJson(result);
    }
}
