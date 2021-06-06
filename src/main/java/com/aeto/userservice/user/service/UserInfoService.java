package com.aeto.userservice.user.service;

import com.aeto.userservice.user.entity.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserInfoService {

    /**
     * Accept user informatin and save it in the DB
     * @param userInfo request parameter to create user
     * @return
     */
    UserInfo saveUserInfoDetails(UserInfo userInfo);

    /**
     * service to get user details
     * @param userId key to identify the user
     * @return user information
     */
    UserInfo getUserDetails(long userId);


    /**
     * service to delete the user details
     * @param userId key to identify the user
     * @return deleted user info
     */
    UserInfo deleteUser(long userId);


    /**
     * service to return the list of users associated with an account
     * @return list of users
     */
    List<UserInfo> getUserList();

}
