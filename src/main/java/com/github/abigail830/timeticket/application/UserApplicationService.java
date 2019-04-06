package com.github.abigail830.timeticket.application;

import com.github.abigail830.timeticket.domain.UserDomain;
import com.github.abigail830.timeticket.domain.UserDomainFactory;
import com.github.abigail830.timeticket.domain.wx.WxDomain;
import com.github.abigail830.timeticket.domain.wx.WxDomainFactory;
import com.github.abigail830.timeticket.domain.wx.WxLoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserApplicationService {

    @Autowired
    WxDomainFactory wxDomainFactory;

    @Autowired
    UserDomainFactory userDomainFactory;

    public String login(String headerCode) {
        final WxDomain wx = wxDomainFactory.build();
        final WxLoginInfo loginInfo = wx.login(headerCode);

        if (wx.isLoginSuccess()) {
            log.info("User {} login Wechat successfully", loginInfo.getOpenid());
            final UserDomain user = userDomainFactory.build();
            user.addUserByOpenId(loginInfo.getOpenid());
        }
        return WxLoginInfo.toJson(loginInfo);
    }


}
