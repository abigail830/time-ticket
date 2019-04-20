package com.github.abigail830.timeticket.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.abigail830.timeticket.util.http.JsonUtil;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private Integer id;
    private String openId;
    private String gender;
    private String nickName;
    private String city;
    private String country;
    private String province;
    private String lang;
    private String avatarUrl;

    private String session_key;

    public User(String openId) {
        this.openId = openId;
    }

    public User(String openId, String gender, String nickName, String city,
                String country, String province, String lang, String avatarUrl) {
        this.openId = openId;
        this.gender = gender;
        this.nickName = nickName;
        this.city = city;
        this.country = country;
        this.province = province;
        this.lang = lang;
        this.avatarUrl = avatarUrl;
    }

    public User(String openId, String session_key) {
        this.openId = openId;
        this.session_key = session_key;
    }

    public boolean isLoginSuccess() {
        return openId != null && session_key != null;

    }

    public static User fromJson(String json) {
        return JsonUtil.toObject(json, User.class);
    }

    public static String toJson(User user) {
        return JsonUtil.toJson(user);
    }
}
