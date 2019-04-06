package com.github.abigail830.timeticket.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDomainFactory {

    @Autowired
    UserRepository userRepository;

    public UserDomain build() {
        return new UserDomain(userRepository);
    }
}
