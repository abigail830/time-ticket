package com.github.abigail830.timeticket.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String openId;

    private String gender;
    private String nickName;
    private String city;
    private String country;
    private String province;
    private String lang;
    private String avatarUrl;

    public User(String openId) {
        this.openId = openId;
    }
}
