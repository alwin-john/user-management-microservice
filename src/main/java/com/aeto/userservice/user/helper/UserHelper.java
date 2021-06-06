package com.aeto.userservice.user.helper;

import com.aeto.userservice.user.dto.RegisterUserDto;
import com.aeto.userservice.user.entity.UserInfo;
import com.aeto.userservice.user.enumeration.RoleType;
import com.aeto.userservice.user.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserHelper {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserUtility userUtility;

    /**
     * Map the request parameter to entity to save into DB
     * @param registerDto register form data
     * @return entity of user info
     */
    public UserInfo bindModelForUserCreation(RegisterUserDto registerDto) {

        UserInfo accountUser = userUtility.getLoggedInUserDetails();
        return UserInfo.builder().userName(registerDto.getUserName())
            .password(encoder.encode(registerDto.getPassword())).accountId(accountUser.getUserId())
            .mobileNo(registerDto.getMobileNo()).userRole(RoleType.USER.toString()).createdDate(LocalDateTime.now()).build();

    }
}
