package com.aeto.userservice.user.controller;

import com.aeto.userservice.user.model.UserInfo;
import com.aeto.userservice.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/accounts")
public class AccountController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login() {
        return new ResponseEntity("hello", HttpStatus.OK);
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<?> signUp() {
        UserInfo userInfo = UserInfo.builder().userName("alwin")
            .mobileNo("123456").password("ss").roleId(1L).build();
        UserInfo SavedUserInfo = userInfoService.saveUserInfoDetails(userInfo);
        return new ResponseEntity(SavedUserInfo, HttpStatus.OK);
    }
}
