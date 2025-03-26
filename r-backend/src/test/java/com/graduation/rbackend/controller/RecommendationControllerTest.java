package com.graduation.rbackend.controller;

import com.graduation.rbackend.config.SecurityConfig;
import com.graduation.rbackend.entity.Course;
import com.graduation.rbackend.entity.Recommendation;
import com.graduation.rbackend.entity.Student;
import com.graduation.rbackend.security.jwt.JwtTokenProvider;
import com.graduation.rbackend.service.RecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RecommendationController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = true)// 自动配置MockMvc，启用过滤器，以便于测试Web控制器
public class RecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private RecommendationService recommendationService;
    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    private List<Recommendation> mockRecommendations;
    private static final String validToken = "validToken";

    @BeforeEach
    void setUp(){
        mockRecommendations = List.of(
                new Recommendation(1L, new Student(), new Course(), 4.5, "Collaborative Filtering"),
                new Recommendation(2L, new Student(), new Course(), 4.7, "Content-Based")

        );
        when(jwtTokenProvider.validateToken(validToken)).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(validToken)).thenReturn(
                new UsernamePasswordAuthenticationToken("user",null,
                        Collections.singletonList(
                                new SimpleGrantedAuthority("ROLE_ADMIN")
                        ))
        );
    }
    @Test
    public void testGetAllRecommendations() throws Exception {
        when(recommendationService.getAllRecommendations()).thenReturn(mockRecommendations);
        mockMvc.perform(get("/api/recommendations")
                .header("Authorization", "Bearer " + validToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
