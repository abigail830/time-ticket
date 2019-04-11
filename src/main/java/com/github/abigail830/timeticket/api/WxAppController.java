package com.github.abigail830.timeticket.api;

import com.github.abigail830.timeticket.application.WxApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    WxApplicationService wxApplicationService;


    @GetMapping("/wxLogin")
    public String login(HttpServletRequest request) {
        String code = request.getHeader("X-WX-Code");
        return wxApplicationService.login(code);
    }

    @GetMapping("/decrypt")
    public String decrypt(HttpServletRequest request) {
        String skey = request.getHeader("skey");
        String encryptedData = request.getHeader("encryptedData");
        String iv = request.getHeader("iv");
        return wxApplicationService.decrypt(skey, encryptedData, iv);
    }

}
