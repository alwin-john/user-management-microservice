package com.aeto.userservice.user.controller;

import com.aeto.userservice.user.Constants.AppConstants;
import com.aeto.userservice.user.Constants.HeaderKey;
import com.aeto.userservice.user.dto.RegisterUserDto;
import com.aeto.userservice.user.entity.UserInfo;
import com.aeto.userservice.user.exception.BusinessErrorException;
import com.aeto.userservice.user.helper.AccountHelper;
import com.aeto.userservice.user.helper.UserHelper;
import com.aeto.userservice.user.model.ApiSuccess;
import com.aeto.userservice.user.service.UserInfoService;
import com.aeto.userservice.user.utility.ResponseBuilder;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/v1/api/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Autowired
    private UserHelper userHelper;

    /**
     * Add a new user to the account
     * @param registerDto request input parameter to add a user to account
     * @return added user information
     * @throws BusinessErrorException
     */
    @ApiOperation(value = "Add a new user to the account. Account can add multiple users to his account",
        notes = " Account user must login into the application. " +
                " Find the token in response header and use that token in Authorization header ( Bearer <your token>)")
    @ApiImplicitParams({
        @ApiImplicitParam(name= HeaderKey.AUTH, paramType = AppConstants.HEADER, required = true)
    })
    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody RegisterUserDto registerDto) throws BusinessErrorException {

        UserInfo userInfo = userHelper.bindModelForUserCreation(registerDto);
        UserInfo savedUserInfo = userInfoService.saveUserInfoDetails(userInfo);
        return responseBuilder.build(new ApiSuccess(HttpStatus.CREATED, savedUserInfo));

    }

    /**
     * Delete the user
     * @param userId key to identify the user
     * @return data contains null value
     * @throws BusinessErrorException
     */
    @ApiOperation(value = "Delete the user from the account",
        notes = " Account user must login into the application. " +
                " Find the token in response header and use that token in Authorization header ( Bearer <your token>)")
    @ApiImplicitParams({
        @ApiImplicitParam(name= HeaderKey.AUTH, paramType = AppConstants.HEADER, required = true)
    })
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") long userId) throws BusinessErrorException {

        UserInfo userInfo = userInfoService.deleteUser(userId);
        return responseBuilder.build(new ApiSuccess(HttpStatus.OK, userInfo));
    }
}
