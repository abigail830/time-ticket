package com.github.abigail830.timeticket.domain.wx;

import com.github.abigail830.timeticket.util.http.JsonUtil;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WxLoginInfo {

    private String session_key;
    private String openid;

    public static String toJson(WxLoginInfo wxLoginInfo) {
        return JsonUtil.toJson(wxLoginInfo);
    }

    public static WxLoginInfo fromJson(String json) {
        return JsonUtil.toObject(json, WxLoginInfo.class);
    }
}
