package com.github.abigail830.timeticket.infrastructure.repository;

import com.github.abigail830.timeticket.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void addUserByOpenId(String openId);

    User updateUser(User user);

    Optional<User> getUserByOpenId(String openId);

    Optional<User> getUserById(String id);

    List<User> getAllUsers();
}
