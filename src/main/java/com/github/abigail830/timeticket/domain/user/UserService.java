package com.github.abigail830.timeticket.domain.user;

import com.github.abigail830.timeticket.infrastructure.InfraException;
import com.github.abigail830.timeticket.util.AESUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;

@Slf4j
public class UserService {

    private UserInfrastructure userInfrastructure;

    @Autowired
    public UserService(UserInfrastructure userInfrastructure) {
        this.userInfrastructure = userInfrastructure;
    }

    public void addUserByOpenId(String openId) {
        userInfrastructure.addUserByOpenId(openId);
    }

    public User updateUser(User user) {
        return userInfrastructure.updateUser(user);
    }

    public User login(String appId, String appSecret, String headerCode) {
        try {
            return userInfrastructure.login(appId, appSecret, headerCode);
        } catch (InfraException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, ex.getMessage());

        }
    }

    public User decryptUser(String appId, String skey, String encryptedData, String iv) {

        validateInputBeforeDecrypt(skey, iv);
        byte[] aesKey = Base64.decodeBase64(skey);
        byte[] aesIV = Base64.decodeBase64(iv);
        byte[] aesCipher = Base64.decodeBase64(encryptedData);

        try {
            byte[] resultByte = AESUtils.decrypt(aesCipher, aesKey, aesIV);
            if (null != resultByte && resultByte.length > 0) {
                String userInfo = new String(resultByte, "UTF-8");
                //validateWatermark(userInfo, appId);
                return User.fromJson(userInfo);
            } else {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Decrypted Fail");
            }
        } catch (InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Decrypted Fail");
        }
    }

    private void validateWatermark(String userInfo, String appId) {
        log.info("userinfo after decrypt is {}", userInfo);
        JsonObject jsons = new JsonParser().parse(userInfo).getAsJsonObject();
        String id = jsons.getAsJsonObject("watermark").get("appId").getAsString();
        if (!StringUtils.equals(id, appId)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, " Illegal Buffer");
        }
    }

    private void validateInputBeforeDecrypt(String skey, String iv) {
        if (StringUtils.length(skey) != 24)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, " Illegal Aes Session Key");

        if (StringUtils.length(iv) != 24)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, " Illegal IV");

    }

    public boolean isValidAssignee(String assigneeOpenId) {
        return assigneeOpenId != null && isExistUser(assigneeOpenId);
    }

    private boolean isExistUser(String openId) {
        return userInfrastructure.getUserByOpenId(openId).isPresent();
    }

    public void createUser(String avatarUrl, String city, String country, String gender,
                           String lang, String nickName, String openId, String province) {
        final User user = new User(openId, gender, nickName, city, country, province, lang, avatarUrl);
        userInfrastructure.addUser(user);
    }
}
