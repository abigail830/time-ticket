package com.github.abigail830.timeticket.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserInfrastructure {

    void addUserByOpenId(String openId);

    User updateUser(User user);

    Optional<User> getUserByOpenId(String openId);

    Optional<User> getUserById(String id);

    List<User> getAllUsers();

    User login(String appId, String appSecret, String headerCode);

}
