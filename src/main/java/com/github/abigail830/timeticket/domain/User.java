package com.github.abigail830.timeticket.domain;

import com.github.abigail830.timeticket.util.http.JsonUtil;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
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

    public static User fromJson(String json) {
        return JsonUtil.toObject(json, User.class);
    }

    public static String toJson(User user) {
        return JsonUtil.toJson(user);
    }
}
