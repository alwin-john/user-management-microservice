package com.aeto.userservice.user.controller;

import com.aeto.userservice.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping()
    public ResponseEntity<?> getUserDetails() {
        return new ResponseEntity("test", HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addUser() {
        return new ResponseEntity("test", HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser() {
        return new ResponseEntity("test", HttpStatus.OK);
    }
}
