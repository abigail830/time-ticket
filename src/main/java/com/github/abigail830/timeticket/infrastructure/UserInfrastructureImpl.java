package com.github.abigail830.timeticket.infrastructure;

import com.github.abigail830.timeticket.domain.user.User;
import com.github.abigail830.timeticket.domain.user.UserInfrastructure;
import com.github.abigail830.timeticket.infrastructure.client.WxClient;
import com.github.abigail830.timeticket.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserInfrastructureImpl implements UserInfrastructure {

    private UserRepository userRepository;
    private WxClient wxClient;

    @Autowired
    public UserInfrastructureImpl(UserRepository userRepository, WxClient wxClient) {
        this.userRepository = userRepository;
        this.wxClient = wxClient;
    }

    @Override
    public void addUserByOpenId(String openId) {
        userRepository.addUserByOpenId(openId);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public Optional<User> getUserByOpenId(String openId) {
        return userRepository.getUserByOpenId(openId);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User login(String appId, String appSecret, String headerCode) {
        return wxClient.login(appId, appSecret, headerCode);
    }
}
