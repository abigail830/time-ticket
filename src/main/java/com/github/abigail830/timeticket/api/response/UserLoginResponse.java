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
@ApiModel(description = "用户登陆返回")
public class UserLoginResponse {

    private String session_key;
    private String openid;

    public static UserLoginResponse fromUser(User user) {
        return UserLoginResponse.builder()
                .session_key(user.getSession_key())
                .openid(user.getOpenId())
                .build();
    }
}
