package com.github.abigail830.timeticket.application;

import com.github.abigail830.timeticket.domain.User;
import com.github.abigail830.timeticket.domain.UserDomain;
import com.github.abigail830.timeticket.domain.UserDomainFactory;
import com.github.abigail830.timeticket.domain.wx.WxDomain;
import com.github.abigail830.timeticket.domain.wx.WxDomainFactory;
import com.github.abigail830.timeticket.domain.wx.WxLoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class WxApplicationService {

    @Autowired
    WxDomainFactory wxDomainFactory;

    @Autowired
    UserDomainFactory userDomainFactory;

    @Value("${app.appId}")
    private String appId;

    @Value("${app.appSecret}")
    private String appSecret;

    public String login(String headerCode) {
        if (headerCode == null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Missing X-WX-Code in header");

        final WxDomain wx = wxDomainFactory.build();
        final WxLoginInfo loginInfo = wx.login(headerCode);
        if (!wx.isLoginSuccess()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, " Fail to login Wechat backend");
        } else {
            log.info("User {} login Wechat successfully", loginInfo.getOpenid());
            userDomainFactory.build(loginInfo.getOpenid());
        }

        return WxLoginInfo.toJson(loginInfo);
    }


    public String decrypt(String skey, String encryptedData, String iv) {

        final WxDomain wx = wxDomainFactory.build();
        final String decryptUser = wx.decryptUser(skey, encryptedData, iv);

        final UserDomain userDomain = userDomainFactory.build();
        final User user = User.fromJson(decryptUser);
        userDomain.updateUser(user);

        return User.toJson(user);

    }




}
