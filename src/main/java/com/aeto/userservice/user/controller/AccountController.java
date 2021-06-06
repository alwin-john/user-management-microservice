package com.aeto.userservice.user.controller;

import com.aeto.userservice.user.Constants.AppConstants;
import com.aeto.userservice.user.Constants.HeaderKey;
import com.aeto.userservice.user.dto.RegisterUserDto;
import com.aeto.userservice.user.entity.UserInfo;
import com.aeto.userservice.user.exception.BusinessErrorException;
import com.aeto.userservice.user.helper.AccountHelper;
import com.aeto.userservice.user.utility.ResponseBuilder;
import com.aeto.userservice.user.model.ApiSuccess;
import com.aeto.userservice.user.service.UserInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/v1/api/accounts")
public class AccountController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Autowired
    private AccountHelper accountHelper;

    /**
     * Create a new account
     * @param registerDto details need to register a user
     * @return created account information
     * @throws BusinessErrorException
     */
    @ApiOperation(value = "Register a new account to the application",
                  notes = "User can create an account in the application. He/she can create it without login ")
    @ApiImplicitParams({
        @ApiImplicitParam(name= HeaderKey.AUTH, paramType = AppConstants.HEADER, required = false)
    })
    @PostMapping(value = "/register")
    public ResponseEntity<?> signUp(@RequestBody RegisterUserDto registerDto) throws BusinessErrorException {

        UserInfo userInfo = accountHelper.bindModelForAccountCreation(registerDto);
        UserInfo savedUserInfo = userInfoService.saveUserInfoDetails(userInfo);
        return responseBuilder.build(new ApiSuccess(HttpStatus.OK, savedUserInfo));

    }

    /**
     * List the users associated with an account
     * @return list of user information associated with an account
     * @throws BusinessErrorException
     */
    @ApiOperation(value = "List the users associated with an account",
        notes = " Account user must login into the application. " +
                " Find the token in response header and use that token in Authorization header ( Bearer <your token>)")
    @ApiImplicitParams({
        @ApiImplicitParam(name= HeaderKey.AUTH, paramType = AppConstants.HEADER, required = true)
    })
    @GetMapping(value = "/users")
    public ResponseEntity<?> getUsers() throws BusinessErrorException {

        List<UserInfo> savedUserInfo = userInfoService.getUserList();
        return responseBuilder.build(new ApiSuccess(HttpStatus.OK, savedUserInfo));

    }

    /**
     * Fetch the user details
     * @param userId key to identify the user
     * @return user information
     * @throws BusinessErrorException
     */
    @ApiOperation(value = "Fetch the user details",
        notes = " User(Account or normal user) must login into the application. " +
            "Find the token in response header and use that token in Authorization header ( Bearer <your token>)")
    @ApiImplicitParams({
        @ApiImplicitParam(name= HeaderKey.AUTH, paramType = AppConstants.HEADER, required = true)
    })
    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getUserDetails(@PathVariable("userId") long userId) throws BusinessErrorException {

        UserInfo userInfo = userInfoService.getUserDetails(userId);
        return responseBuilder.build(new ApiSuccess(HttpStatus.OK, userInfo));
    }
}
