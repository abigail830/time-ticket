package com.github.abigail830.timeticket.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDomain {

    private UserRepository userRepository;

    private User user;

    public UserDomain(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDomain(String openId, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.user = this.userRepository.addUser(User.builder().openId(openId).build());
    }

    public void updateUser(User user) {
        this.user = user;
        userRepository.updateUser(this.user);
    }

    public boolean isExistUser(String openId) {
        return userRepository.getUserByOpenId(openId).isPresent();
    }

    User getUserByOpenId(String openId) {
        return userRepository.getUserByOpenId(openId).get();
    }

}
