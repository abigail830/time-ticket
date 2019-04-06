package com.github.abigail830.timeticket.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void addUser(User user);

    void updateUser(User user);

    Optional<User> getUserByOpenId(String openId);

    Optional<User> getUserById(String id);

    List<User> getAllUsers();

}
