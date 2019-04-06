package com.github.abigail830.timeticket.domain.wx;

import com.github.abigail830.timeticket.util.AESUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;

@Setter
@Getter
@Slf4j
public class WxDomain {

    private String appId;
    private String appSecret;
    private WxCommonInterface wxCommonInterface;

    private WxLoginInfo wxLoginInfo;


    public WxDomain(String appId, String appSecret, WxCommonInterface wxCommonInterface) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.wxCommonInterface = wxCommonInterface;
    }

    public WxLoginInfo login(String headerCode) {
        try {
            wxLoginInfo = wxCommonInterface.login(appId, appSecret, headerCode);
            return wxLoginInfo;

        } catch (JsonSyntaxException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "False to parse the result to json from WeChat backend.");

        } catch (RuntimeException runtimeEx) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "Exception when http login WeChat backend");
        }
    }

    public boolean isLoginSuccess() {
        if (wxLoginInfo.getOpenid() != null)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }


    public String decryptUser(String skey, String encryptedData, String iv) {

        validateInputBeforeDecrypt(skey, iv);

        byte[] aesKey = Base64.decodeBase64(skey);
        byte[] aesIV = Base64.decodeBase64(iv);
        byte[] aesCipher = Base64.decodeBase64(encryptedData);

        try {
            byte[] resultByte = AESUtils.decrypt(aesCipher, aesKey, aesIV);

            if (null != resultByte && resultByte.length > 0) {
                String userInfo = new String(resultByte, "UTF-8");
                JsonObject jsons = new JsonParser().parse(userInfo).getAsJsonObject();
                String id = jsons.getAsJsonObject("watermark").get("appId").getAsString();
                if (!StringUtils.equals(id, appId)) {
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, " Illegal Buffer");
                } else {
                    return userInfo;
                }
            } else {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Decrypted Fail");
            }

        } catch (InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Decrypted Fail");
        }
    }

    private void validateInputBeforeDecrypt(String skey, String iv) {
        if (StringUtils.length(skey) != 24)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, " Illegal Aes Session Key");

        if (StringUtils.length(iv) != 24)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, " Illegal IV");

    }

}
