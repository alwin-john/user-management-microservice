package com.aeto.userservice.user.service;

import com.aeto.userservice.user.Constants.ErrorName;
import com.aeto.userservice.user.entity.UserInfo;
import com.aeto.userservice.user.exception.BusinessErrorException;
import com.aeto.userservice.user.model.ResultObject;
import com.aeto.userservice.user.repository.UserRepository;
import com.aeto.userservice.user.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserRepository userRepository;

    private UserUtility userUtility;

    @Autowired
    public UserInfoServiceImpl(UserRepository userRepository, UserUtility userUtility) {
        this.userRepository = userRepository;
        this.userUtility = userUtility;
    }

    @Override
    public UserInfo saveUserInfoDetails(UserInfo userInfo) throws BusinessErrorException {

        Optional.ofNullable(userRepository.findByUserName(userInfo.getUserName()))
            .ifPresent(u -> {
                ResultObject resultObject = ResultObject.builder()
                    .messageKey(ErrorName.EXISTING_USER).logLevel(LogLevel.ERROR).build();
                throw new BusinessErrorException(resultObject);
            });
        return userRepository.save(userInfo);
    }

    @Override
    public UserInfo getUserDetails(long userId) throws BusinessErrorException {

        UserInfo user = userRepository.findByUserId(userId);
        userUtility.isUserExist(user);
        return user;
    }

    @Override
    public UserInfo deleteUser(long userId) throws BusinessErrorException {

        UserInfo loggedInUser = userUtility.getLoggedInUserDetails();
        UserInfo user = userRepository.findByUserId(userId);
        userUtility.isUserExist(user);
        if (loggedInUser.getUserId() != user.getAccountId()) {
            ResultObject resultObject = ResultObject.builder()
                .messageKey(ErrorName.USER_NOT_FOUND).logLevel(LogLevel.ERROR).build();
            throw new BusinessErrorException(resultObject);
        }
        return userRepository.deleteById(userId);
    }

    @Override
    public List<UserInfo> getUserList() throws BusinessErrorException {

        UserInfo user = userUtility.getLoggedInUserDetails();
        userUtility.isUserExist(user);
        return userRepository.findByAccountId(user.getUserId());
    }

}
