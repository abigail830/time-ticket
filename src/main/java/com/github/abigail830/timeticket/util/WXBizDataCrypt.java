package com.github.abigail830.timeticket.util;

import com.github.abigail830.timeticket.util.http.JsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;

public class WXBizDataCrypt {

    private String appId;

    private String sessionKey;

    public WXBizDataCrypt(String appId, String sessionKey) {
        this.appId = appId;
        this.sessionKey = sessionKey;
    }


    public String decryptData(String encryptedData, String iv) {

        Map<String, Object> map = new HashMap<>();

        if (StringUtils.length(sessionKey) != 24) {
            map.put("errorCode", "ErrorCode::$IllegalAesKey;");
            return JsonUtil.toJson(map);
        }
        // 对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
        byte[] aesKey = Base64.decodeBase64(sessionKey);

        if (StringUtils.length(iv) != 24) {
            map.put("errorCode", "ErrorCode::$IllegalIv;");
            return JsonUtil.toJson(map);
        }

        // 对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
        byte[] aesIV = Base64.decodeBase64(iv);

        // 对称解密的目标密文为 Base64_Decode(encryptedData)
        byte[] aesCipher = Base64.decodeBase64(encryptedData);


        try {
            byte[] resultByte = AESUtils.decrypt(aesCipher, aesKey, aesIV);

            if (null != resultByte && resultByte.length > 0) {
                String userInfo = new String(resultByte, "UTF-8");
                map.put("code", "0000");
                map.put("msg", "succeed");
                map.put("userInfo", JsonUtil.toObject(userInfo, Map.class));

                // watermark参数说明：
                // 参数  类型  说明
                // watermark   OBJECT  数据水印
                // appId   String  敏感数据归属appid，开发者可校验此参数与自身appid是否一致
                // timestamp   DateInt 敏感数据获取的时间戳, 开发者可以用于数据时效性校验'

                // 根据微信建议：敏感数据归属appid，开发者可校验此参数与自身appid是否一致
                // if decrypted['watermark']['appId'] != self.appId:
                JsonObject jsons = new JsonParser().parse(userInfo).getAsJsonObject();
                String id = jsons.getAsJsonObject("watermark").get("appId").getAsString();
                if (!StringUtils.equals(id, appId)) {
                    Map<String, Object> errorMap = new HashMap<>();
                    errorMap.put("errorCode", "ErrorCode::$IllegalBuffer;");
                    return JsonUtil.toJson(errorMap);
                }
            } else {
                map.put("code", "1000");
                map.put("msg", "false");
            }
        } catch (InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return JsonUtil.toJson(map);
    }

}