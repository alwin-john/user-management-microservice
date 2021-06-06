package com.aeto.userservice.controller;

import com.aeto.userservice.config.ContainerConfig;
import com.aeto.userservice.user.dto.RegisterUserDto;
import com.aeto.userservice.user.model.LoginCreds;
import com.aeto.userservice.user.repository.UserRepository;
import com.aeto.userservice.utility.JsonUtility;
import org.hamcrest.core.IsNull;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = ContainerConfig.getInstance();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void init() {
        postgreSQLContainer.start();
    }

    @Test
    public void addUserTest() throws Exception {
        RegisterUserDto registerUserDto = getRegisterUserDto();
        String registerUser = JsonUtility.writeObjectAsString(registerUserDto);
        //login
        MvcResult result = mockMvc.perform(post("/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.ALL)
            .content(JsonUtility.writeObjectAsString(getLogincredentials())))
            .andExpect(status().isOk()).andReturn();
        //add user
        MvcResult mvcResult = mockMvc.perform(post("/v1/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", result.getResponse().getHeader("Authorization"))
            .accept(MediaType.ALL)
            .content(registerUser))
            .andExpect(status().isCreated()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        JSONObject userInfo = JsonUtility.getUserInfo(response);
        assertThat(userInfo.getString("userId"), is(IsNull.notNullValue()));
        assertThat(userInfo.getString("userName"), equalToIgnoringCase("alwin1"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        RegisterUserDto registerUserDto = getRegisterUserDto();
        long rowCount = userRepository.count()-1;
        //login
        MvcResult result = mockMvc.perform(post("/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.ALL)
            .content(JsonUtility.writeObjectAsString(getLogincredentials())))
            .andExpect(status().isOk()).andReturn();
        //delete user
        MvcResult userDetails = mockMvc.perform(delete("/v1/api/user/250")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", result.getResponse().getHeader("Authorization"))
            .accept(MediaType.ALL))
            .andExpect(status().isOk()).andReturn();
        assertThat(userRepository.count(), is(rowCount));
    }

    private RegisterUserDto getRegisterUserDto() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setUserName("alwin1");
        registerUserDto.setMobileNo("123456789");
        registerUserDto.setPassword("password");
        return registerUserDto;
    }

    private LoginCreds getLogincredentials() {
        LoginCreds loginCreds = new LoginCreds();
        loginCreds.setUserName("test");
        loginCreds.setPassword("password");
        return loginCreds;
    }
}
