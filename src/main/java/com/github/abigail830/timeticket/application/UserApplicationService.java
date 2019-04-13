package com.github.abigail830.timeticket.application;

import com.github.abigail830.timeticket.domain.user.User;
import com.github.abigail830.timeticket.domain.user.UserInfrastructure;
import com.github.abigail830.timeticket.domain.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class UserApplicationService {

    private final UserService userService;

    @Autowired
    UserInfrastructure userInfrastructure;

    @Value("${app.appId}")
    private String appId;

    @Value("${app.appSecret}")
    private String appSecret;

    public UserApplicationService(UserInfrastructure userInfrastructure) {
        this.userInfrastructure = userInfrastructure;
        this.userService = new UserService(appId, appSecret, userInfrastructure);
    }

    public User login(String headerCode) {
        validHeaderCode(headerCode);
        final User user = userService.login(headerCode);
        if (user.isLoginSuccess()) {
            userService.addUserByOpenId(user.getOpenId());
            return user;
        } else
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, " Fail to login Wechat backend");
    }

    private void validHeaderCode(String headerCode) {
        if (headerCode == null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Missing X-WX-Code in header");
    }


    public User decrypt(String skey, String encryptedData, String iv) {
        final User newUser = userService.decryptUser(skey, encryptedData, iv);
        userService.updateUser(newUser);

        return newUser;

    }




}