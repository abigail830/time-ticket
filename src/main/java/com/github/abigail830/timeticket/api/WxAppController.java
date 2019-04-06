package com.github.abigail830.timeticket.api;

import com.github.abigail830.timeticket.api.dto.WxDecryptResponse;
import com.github.abigail830.timeticket.application.UserApplicationService;
import com.github.abigail830.timeticket.util.WXBizDataCrypt;
import com.github.abigail830.timeticket.util.http.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @Autowired
    UserApplicationService userApplicationService;


    @GetMapping("/wxLogin")
    public String login(HttpServletRequest request) {
        String code = request.getHeader("X-WX-Code");
        if (code == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing X-WX-Code in header");
        else
            return userApplicationService.login(code);
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
