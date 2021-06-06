package com.aeto.userservice.user.dto;

import lombok.Data;

@Data
public class RegisterUserDto {

    private String userName;

    private String password;

    private String mobileNo;
}
