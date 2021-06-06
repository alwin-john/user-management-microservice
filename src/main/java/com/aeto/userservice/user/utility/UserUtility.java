package com.aeto.userservice.user.utility;

import com.aeto.userservice.user.Constants.ErrorName;
import com.aeto.userservice.user.entity.UserInfo;
import com.aeto.userservice.user.exception.BusinessErrorException;
import com.aeto.userservice.user.model.ResultObject;
import com.aeto.userservice.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UserUtility {

    @Autowired
    private UserRepository userRepository;

    /**
     * Check whether the user exist in DB
     * @param user Inputted user details
     * @throws BusinessErrorException
     */
    public void isUserExist(UserInfo user) throws BusinessErrorException {
        Optional.ofNullable(user).<BusinessErrorException>orElseThrow(() -> {
            ResultObject resultObject = ResultObject.builder()
                .messageKey(ErrorName.USER_NOT_FOUND).logLevel(LogLevel.ERROR).build();
            throw new BusinessErrorException(resultObject);
        });
    }

    /**
     * utility to class to find the information of logged in class
     * @return
     */
    public UserInfo getLoggedInUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUserName(auth.getName());
    }
}
