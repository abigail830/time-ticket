package com.github.abigail830.timeticket.api;

import com.github.abigail830.timeticket.api.dto.WxDecryptResponse;
import com.github.abigail830.timeticket.api.dto.WxLoginResponse;
import com.github.abigail830.timeticket.util.WXBizDataCrypt;
import com.github.abigail830.timeticket.util.http.HttpClientUtil;
import com.github.abigail830.timeticket.util.http.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 小程序相关登录和解密接口
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class WxAppController {

    @Value("${app.appId}")
    private String appId;

    @Value("${app.appSecret}")
    private String appSecret;


    @GetMapping("/wxLogin")
    public String login(HttpServletRequest request) {
        String code = request.getHeader("X-WX-Code");
        String resultData = HttpClientUtil.instance().getData(
                "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId +
                        "&secret=" + appSecret +
                        "&grant_type=authorization_code" +
                        "&js_code=" + code);

        WxLoginResponse wxLoginResponse = JsonUtil.toObject(resultData, WxLoginResponse.class);
        if (wxLoginResponse.getOpenid() != null) {
            //TODO: to insert user
//            userService.createUser(new UserInfo(wxLoginResponse.getOpenid()));
        } else {
            log.error("not able to process ws response " + resultData);
            log.error("Fail to get openID from wechat API");
        }

        return resultData;
    }

    @GetMapping("/decrypt")
    public String decrypt(HttpServletRequest request) {
        String skey = request.getHeader("skey");
        String encryptedData = request.getHeader("encryptedData");
        String iv = request.getHeader("iv");
        WXBizDataCrypt biz = new WXBizDataCrypt(appId, skey);

        String resultDate = biz.decryptData(encryptedData, iv);
        WxDecryptResponse wxDecryptResponse = JsonUtil.toObject(resultDate, WxDecryptResponse.class);
        log.info("wxDecryptResponse: {}", wxDecryptResponse);
        if (wxDecryptResponse.getErrorCode() == null) {
            //TODO: to update user info after authorize
//            userService.updateUser(wxDecryptResponse.getUserInfo());
//            log.info("Updated user info for user {}", wxDecryptResponse.getUserInfo().getOpenId());
        } else {
            log.error("Error occur during decrypt wechat message with error code: {}", wxDecryptResponse.getErrorCode());
        }

        return resultDate;
    }

}
