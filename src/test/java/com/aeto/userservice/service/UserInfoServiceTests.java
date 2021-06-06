package com.aeto.userservice.service;

import com.aeto.userservice.user.entity.UserInfo;
import com.aeto.userservice.user.enumeration.RoleType;
import com.aeto.userservice.user.exception.BusinessErrorException;
import com.aeto.userservice.user.repository.UserRepository;
import com.aeto.userservice.user.service.UserInfoServiceImpl;
import com.aeto.userservice.user.utility.UserUtility;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

public class UserInfoServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserUtility userUtility;

    private UserInfoServiceImpl userInfoService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        userInfoService = new UserInfoServiceImpl(userRepository, userUtility);
        setSecurityContext();
    }

    @After
    public void clear() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void saveUserInfoDetailsTest() {
        UserInfo userInfo = getUserInfo();
        when(userRepository.save(any())).thenReturn(userInfo);
        UserInfo savedUser = userInfoService.saveUserInfoDetails(userInfo);
        assertThat(savedUser, equalTo(userInfo));
    }

    @Test
    public void AlreadyExistingUserInfoDetailsTest() {
        try {
            UserInfo userInfo = getUserInfo();
            when(userRepository.save(any())).thenReturn(userInfo);
            when(userRepository.findByUserName(anyString())).thenReturn(userInfo);
            UserInfo savedUser = userInfoService.saveUserInfoDetails(userInfo);
            assertThat(savedUser, equalTo(userInfo));
        } catch (BusinessErrorException ex) {
            assertTrue(ex instanceof BusinessErrorException);
            assertThat("exception.user.existinguser", equalTo(ex.getResultObject().getMessageKey()));
        }
    }

    @Test
    public void getUserDetailsTest() {
        UserInfo userInfo = getUserInfo();
        when(userRepository.findByUserId(anyLong())).thenReturn(userInfo);
        UserInfo user = userInfoService.getUserDetails(1L);
        assertThat(user, equalTo(userInfo));
    }

    @Test
    public void getUserDetailsTest_NoUser() {
        try {
            UserInfo userInfo = getUserInfo();
            Mockito.doThrow(new BusinessErrorException()).doNothing().when(userUtility).isUserExist(any());
            userInfoService.getUserDetails(1L);
        } catch (BusinessErrorException ex) {
            assertTrue(ex instanceof BusinessErrorException);
        }
    }

    @Test
    public void deleteUserTest() {
        UserInfo userInfo = getUserInfo();
        UserInfo loggedInUserInfo = getLoggedInUserInfo1();
        when(userUtility.getLoggedInUserDetails()).thenReturn(loggedInUserInfo);
        when(userRepository.findByUserId(anyLong())).thenReturn(userInfo);
        when(userRepository.deleteById(anyLong())).thenReturn(userInfo);
        UserInfo user = userInfoService.deleteUser(1L);
        assertThat(user, equalTo(userInfo));
    }

    @Test
    public void deleteUserTest_NoUser() {
        try {
            UserInfo userInfo = getUserInfo();
            Mockito.doThrow(new BusinessErrorException()).doNothing().when(userUtility).isUserExist(any());
            UserInfo user = userInfoService.deleteUser(1L);
        } catch (BusinessErrorException ex) {
            assertTrue(ex instanceof BusinessErrorException);
        }
    }

    @Test
    public void deleteUserTest_NoUserInAccount() {
        try {
            UserInfo userInfo = getUserInfo();
            UserInfo loggedInUser = getLoggedInUserInfo();
            when(userUtility.getLoggedInUserDetails()).thenReturn(loggedInUser);
            when(userRepository.findByUserId(anyLong())).thenReturn(userInfo);
            UserInfo user = userInfoService.deleteUser(1L);
        } catch (BusinessErrorException ex) {
            assertTrue(ex instanceof BusinessErrorException);
        }
    }

    @Test
    public void getUserListTest() {

        List<UserInfo> userInfoList = getUserInfoList();
        when(userRepository.findByAccountId(anyLong())).thenReturn(userInfoList);
        when(userUtility.getLoggedInUserDetails()).thenReturn(getUserInfo());
        List<UserInfo> userList = userInfoService.getUserList();
        assertThat(userList, equalTo(userInfoList));
    }

    @Test
    public void getUserListTest_NoUser() {
        try {
            UserInfo userInfo = getUserInfo();
            Mockito.doThrow(new BusinessErrorException()).doNothing().when(userUtility).isUserExist(any());
            userInfoService.getUserList();
        } catch (BusinessErrorException ex) {
            assertTrue(ex instanceof BusinessErrorException);
        }
    }

    private void setSecurityContext() {
        List<String> authorities = new ArrayList<>();
        authorities.add("test");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            "Alwin", null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private UserInfo getUserInfo() {
        return UserInfo.builder().userId(2).accountId(3).userName("testAccount")
            .userRole(RoleType.USER.toString()).mobileNo("1234567890").build();
    }

    private UserInfo getLoggedInUserInfo() {
        return UserInfo.builder().userId(1).userName("testAccount")
            .userRole(RoleType.ACCOUNT.toString()).mobileNo("1234567890").build();
    }

    private UserInfo getLoggedInUserInfo1() {
        return UserInfo.builder().userId(3).userName("testAccount")
            .userRole(RoleType.ACCOUNT.toString()).mobileNo("1234567890").build();
    }

    private List<UserInfo> getUserInfoList() {
        List<UserInfo> userList = new ArrayList<>();
        UserInfo user1 = UserInfo.builder().userName("Admin")
            .userRole(RoleType.ACCOUNT.toString()).mobileNo("1234567890").build();
        UserInfo user2 = UserInfo.builder().userName("Admin")
            .userRole(RoleType.ACCOUNT.toString()).mobileNo("1234567890").build();
        userList.add(user1);
        userList.add(user2);
        return userList;
    }
}
