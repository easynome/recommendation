package com.graduation.rbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.rbackend.RBackendApplication;
import com.graduation.rbackend.dto.LoginRequestDTO;
import com.graduation.rbackend.service.AuthService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = RBackendApplication.class)
@WebMvcTest(AuthController.class)
//@AutoConfigureMockMvc(addFilters = true)
public class AuthControllerTest {


    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper=new ObjectMapper();

    @MockitoBean
    private AuthService authService;



    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new AuthController(authService))
                .build();
    }

    @Test
    void testLoginSuccess() throws Exception {
        // Mocking AuthService login method for successful login
        String username = "testUser";
        String password = "testPassword";
        String mockToken = "mockJwtToken";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(username, password);
        when(authService.login(username, password)).thenReturn(mockToken);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(mockToken));

        verify(authService, times(1)).login(username, password);
    }

    @Test
    void testLoginBadCredentials() throws Exception {
        // Mocking AuthService login method for failed login due to bad credentials
        String username = "testUser";
        String password = "wrongPassword";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(username, password);
        when(authService.login(username, password)).thenThrow(new BadCredentialsException("密码错误"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("密码错误"));

        verify(authService, times(1)).login(username, password);
    }

    @Test
    void testLoginUserNotFound() throws Exception {
        // Mocking AuthService login method for failed login due to user not found
        String username = "nonExistentUser";
        String password = "somePassword";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(username, password);
        when(authService.login(username, password)).thenThrow(new BadCredentialsException("用户不存在"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("用户不存在"));

        verify(authService, times(1)).login(username, password);
    }

    @Test
    void testPublicEndpoint() throws Exception {
        mockMvc.perform(get("/api/auth/public"))
                .andExpect(status().isOk())
                .andExpect(content().string("这是一个公共接口，任何人都可以访问"));
    }

    @Test
    void testProtectedEndpoint() throws Exception {
        mockMvc.perform(get("/api/auth/protected")
                        .header("Authorization", "Bearer validJwtToken"))
                .andExpect(status().isOk())
                .andExpect(content().string("🔒 这是一个受保护接口，您已通过认证！"));
    }
    @Test
    void testLoginEmptyUsername() throws Exception {
        String username = "";
        String password = "testPassword";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(username, password);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isUnauthorized()) // ✅ 验证状态码为 401
                .andExpect(jsonPath("$.error").value("用户名不能为空")); // ✅ 验证错误信息

        verify(authService, never()).login(anyString(), anyString()); // ✅ 确保 authService 未被调用
    }

    @Test
    void testLoginEmptyPassword() throws Exception {
        String username = "testUser";
        String password = "";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(username, password);
        when(authService.login(username, password)).thenThrow(new BadCredentialsException("密码不能为空"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("密码不能为空"));

        verify(authService, times(1)).login(username, password);
    }
}
