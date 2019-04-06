package com.github.abigail830.timeticket.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDomain {

    private UserRepository userRepository;

    public void addUserByOpenId(String openId) {
        User user = User.builder().openId(openId).build();
        userRepository.addUser(user);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public boolean isExistUser(String openId) {
        return userRepository.getUserByOpenId(openId).isPresent();
    }

    User getUserByOpenId(String openId) {
        return userRepository.getUserByOpenId(openId).get();
    }

}
