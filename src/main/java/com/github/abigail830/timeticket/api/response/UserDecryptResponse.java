package com.github.abigail830.timeticket.api.response;

import com.github.abigail830.timeticket.domain.user.User;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户授权解密返回")
public class UserDecryptResponse {

    private Integer id;
    private String openId;
    private String gender;
    private String nickName;
    private String city;
    private String country;
    private String province;
    private String lang;
    private String avatarUrl;

    public static UserDecryptResponse fromUser(User user) {
        return UserDecryptResponse.builder()
                .openId(user.getOpenId())
                .gender(user.getGender())
                .nickName(user.getNickName())
                .city(user.getCity())
                .country(user.getCountry())
                .province(user.getProvince())
                .lang(user.getLang())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}
