package com.edu.portal.service.impl;

import com.edu.common.util.HttpClientUtil;
import com.edu.portal.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Value("${USER_LOGOUT_BASE}")
    private String USER_LOGOUT_BASE;
    @Value("${USER_LOGOUT}")
    private String USER_LOGOUT;
    @Override
    public void logout(String token) {
        HttpClientUtil.doGet(USER_LOGOUT_BASE + USER_LOGOUT + token);
    }
}
