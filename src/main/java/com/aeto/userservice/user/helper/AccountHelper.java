package com.aeto.userservice.user.helper;

import com.aeto.userservice.user.dto.RegisterUserDto;
import com.aeto.userservice.user.entity.UserInfo;
import com.aeto.userservice.user.enumeration.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccountHelper {

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserInfo bindModelForAccountCreation(RegisterUserDto registerDto) {

        return UserInfo.builder().userName(registerDto.getUserName())
            .password(encoder.encode(registerDto.getPassword()))
            .mobileNo(registerDto.getMobileNo()).userRole(RoleType.ACCOUNT.toString()).createdDate(LocalDateTime.now()).build();

    }
}
