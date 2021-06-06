package com.aeto.userservice.user.controller;

import com.aeto.userservice.user.exception.BusinessErrorException;
import com.aeto.userservice.user.model.LoginCreds;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    /**
     * Login using registered credentials
     * @param loginCreds details need to login
     * @return Jwt token in header
     * @throws BusinessErrorException
     */
    @ApiOperation(value = "Login into the application",
        notes = "Use the token received in the response header to get access to other functions")
    @PostMapping(value = "/auth")
    public void login(@RequestBody LoginCreds loginCreds){

    }
}
