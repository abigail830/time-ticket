package com.github.abigail830.timeticket.infrastructure.client;

import com.github.abigail830.timeticket.domain.user.User;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WxLoginResponse {

    private String session_key;
    private String openid;

    public boolean isValidResponse() {
        return this.openid != null && this.session_key != null;
    }

    public User toUser() {
        return new User(this.openid, this.session_key);
    }


}
