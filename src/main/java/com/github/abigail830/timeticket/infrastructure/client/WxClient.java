package com.github.abigail830.timeticket.infrastructure.client;

import com.github.abigail830.timeticket.domain.user.User;

public interface WxClient {

    User login(String appId, String appSecret, String headerCode);
}
