package com.github.abigail830.timeticket.infrastructure;

import com.github.abigail830.timeticket.domain.User;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private Integer id;
    private String openId;
    private String gender;
    private String nickName;
    private String city;
    private String country;
    private String province;
    private String lang;
    private String avatarUrl;

    public User toUser() {
        return User.builder()
                .nickName(this.nickName)
                .lang(this.lang)
                .province(this.province)
                .country(this.country)
                .city(this.city)
                .avatarUrl(this.avatarUrl)
                .gender(this.gender)
                .openId(this.openId)
                .id(this.id)
                .build();
    }
}
