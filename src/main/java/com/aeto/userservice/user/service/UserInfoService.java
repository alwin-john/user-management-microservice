package com.aeto.userservice.user.service;

import com.aeto.userservice.user.model.UserInfo;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoService {

    public UserInfo saveUserInfoDetails(UserInfo userInfo);

}
