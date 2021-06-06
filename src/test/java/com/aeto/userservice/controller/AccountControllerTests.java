package com.aeto.userservice.controller;

import com.aeto.userservice.config.ContainerConfig;
import com.aeto.userservice.user.dto.RegisterUserDto;
import com.aeto.userservice.user.model.LoginCreds;
import com.aeto.userservice.utility.JsonUtility;
import org.hamcrest.core.IsNull;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class AccountControllerTests {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = ContainerConfig.getInstance();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {
        postgreSQLContainer.start();
    }

    @Test
    @Order(1)
    public void signUpTest() throws Exception {
        RegisterUserDto registerUserDto = getRegisterUserDto();
        String registerUser = JsonUtility.writeObjectAsString(registerUserDto);
        MvcResult mvcResult = mockMvc.perform(post("/v1/api/accounts/register")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.ALL)
            .content(registerUser))
            .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        JSONObject userInfo = JsonUtility.getUserInfo(response);
        assertThat(userInfo.getString("userId"), is(IsNull.notNullValue()));
        assertThat(userInfo.getString("userName"), equalToIgnoringCase("alwin"));
    }

    @Test
    @Order(2)
    public void getUsersTest() throws Exception {
        //login
        MvcResult result = mockMvc.perform(post("/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.ALL)
            .content(JsonUtility.writeObjectAsString(getLogincredentials())))
            .andExpect(status().isOk()).andReturn();
        mockMvc.perform(get("/v1/api/accounts/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", result.getResponse().getHeader("Authorization"))
            .accept(MediaType.ALL))
            .andExpect(status().isOk());
    }

    private RegisterUserDto getRegisterUserDto() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setUserName("alwin");
        registerUserDto.setMobileNo("123456789");
        registerUserDto.setPassword("password");
        return registerUserDto;
    }

    @Test
    public void getUserTest() throws Exception {
        RegisterUserDto registerUserDto = getRegisterUserDto();
        String registerUser = JsonUtility.writeObjectAsString(registerUserDto);
        //login
        MvcResult result = mockMvc.perform(post("/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.ALL)
            .content(JsonUtility.writeObjectAsString(getLogincredentials1())))
            .andExpect(status().isOk()).andReturn();
        //add user
        MvcResult userDetails = mockMvc.perform(get("/v1/api/accounts/250")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", result.getResponse().getHeader("Authorization"))
            .accept(MediaType.ALL))
            .andExpect(status().isOk()).andReturn();
        String response = userDetails.getResponse().getContentAsString();
        JSONObject userInfo = JsonUtility.getUserInfo(response);
        assertThat(userInfo.getString("userId"), is(IsNull.notNullValue()));
        assertThat(userInfo.getString("userName"), equalToIgnoringCase("testUser"));
    }

    private LoginCreds getLogincredentials() {
        LoginCreds loginCreds = new LoginCreds();
        loginCreds.setUserName("alwin");
        loginCreds.setPassword("password");
        return loginCreds;
    }

    private LoginCreds getLogincredentials1() {
        LoginCreds loginCreds = new LoginCreds();
        loginCreds.setUserName("test");
        loginCreds.setPassword("password");
        return loginCreds;
    }
}
