package com.github.abigail830.timeticket.api;

import com.github.abigail830.timeticket.api.response.UserDecryptResponse;
import com.github.abigail830.timeticket.api.response.UserLoginResponse;
import com.github.abigail830.timeticket.application.UserApplicationService;
import com.github.abigail830.timeticket.domain.user.User;
import com.github.abigail830.timeticket.util.http.JsonUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/user")
@Slf4j
@Api(description = "小程序相关登录和解密API")
public class UserController {

    @Autowired
    UserApplicationService userApplicationService;


    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        String code = request.getHeader("X-WX-Code");

        final User login = userApplicationService.login(code);
        return JsonUtil.toJson(UserLoginResponse.fromUser(login));
    }

    @GetMapping("/decrypt")
    public String decrypt(HttpServletRequest request) {
        String skey = request.getHeader("skey");
        String encryptedData = request.getHeader("encryptedData");
        String iv = request.getHeader("iv");

        final User user = userApplicationService.decrypt(skey, encryptedData, iv);
        return JsonUtil.toJson(UserDecryptResponse.fromUser(user));
    }

}
