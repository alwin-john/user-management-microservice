package com.aeto.userservice.user.service;

import com.aeto.userservice.user.model.UserInfo;
import com.aeto.userservice.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo saveUserInfoDetails(UserInfo userInfo) {

        return userRepository.save(userInfo);
    }
}
