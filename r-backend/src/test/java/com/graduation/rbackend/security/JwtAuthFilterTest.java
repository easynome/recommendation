package com.graduation.rbackend.security;

import com.graduation.rbackend.security.jwt.JwtAuthFilter;
import com.graduation.rbackend.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class JwtAuthFilterTest {
    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private FilterChain filterChain;


    private MockHttpServletRequest request;

    private MockHttpServletResponse response;

    private static final String VALID_TOKEN = "validToken";
    private static final String INVALID_TOKEN = "invalidToken";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        SecurityContextHolder.clearContext();//清空Security上下文，避免影响其他测试用例
    }

    //✅ 测试 1：有效 Token - 认证成功
    @Test
    void testDoFilter_ValidToken_Success() throws ServletException, IOException {
        // 模拟请求头中带有正确 Token
        request.addHeader("Authorization", "Bearer " + VALID_TOKEN);

        // Mock Token 验证成功
        when(jwtTokenProvider.validateToken(VALID_TOKEN)).thenReturn(true);

        //创建模拟的Authentication对象，用于模拟获取认证信息
        Authentication mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getAuthorities()).thenReturn(Collections.emptyList());

        //Mock获取认证信息的行为，确保返回的Authentication对象不为空
        //SecurityContextHolder.getContext().getAuthentication() 在 Mock 环境下默认返回 null
//        when(jwtTokenProvider.getAuthentication(VALID_TOKEN)).thenReturn(SecurityContextHolder.getContext().getAuthentication());
        when(jwtTokenProvider.getAuthentication(VALID_TOKEN)).thenReturn(mockAuthentication);

        // 执行过滤器
        jwtAuthFilter.doFilter(request, response, filterChain);

        // 验证 SecurityContextHolder 已正确设置
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();

        // 验证 FilterChain 继续执行
        verify(filterChain, times(1)).doFilter(request, response);
    }
    //✅ 测试 2：无效 Token - 认证失败返回401
    @Test
    void testDoFilter_InvalidToken_Failure() throws ServletException, IOException {
        // 验证 INVALID_TOKEN 是否为合法的字符串格式
        if (INVALID_TOKEN == null || INVALID_TOKEN.isEmpty()) {
            throw new IllegalArgumentException("INVALID_TOKEN must not be null or empty.");
        }
        //模拟请求头中带有错误Token
        request.addHeader("Authorization", "Bearer " + INVALID_TOKEN);

        //Mock Token验证失败
        when(jwtTokenProvider.validateToken(INVALID_TOKEN)).thenReturn(false);

        //执行过滤器
        jwtAuthFilter.doFilter(request, response, filterChain);

        //验证SecurityContextHolder是否被正确设置
        // 验证 SecurityContextHolder 是否被正确设置为匿名用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNull(); // ➤ 无效 Token，不应设置任何认证信息

        //验证返回的响应状态码为401
        assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
        //验证FilterChain不再继续执行
        verify(filterChain,never()).doFilter(any(),any());
    }

    //✅ 测试 3：无Token - 请求继续（无需认证）
    @Test
    void testDoFilter_NoToken() throws ServletException, IOException {

        // ✅ 明确设置路径为 `/api/auth/login`（无需 Token 放行）
        request.setRequestURI("/api/auth/login");
        //请求头无Token（模拟未认证访问）
        jwtAuthFilter.doFilter(request, response, filterChain);

        //验证SecurityContextHolder是否被正确设置
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        //验证FilterChain继续执行
        verify(filterChain,times(1)).doFilter(request,response);
    }

    //✅ 测试 4：Token过期 - 返回401
    @Test
    void testDoFilter_ExpiredToken() throws ServletException, IOException {
        //模拟请求头中带有过期Token
        request.addHeader("Authorization", "Bearer " + INVALID_TOKEN);

        //Mock Token验证失败
        when(jwtTokenProvider.validateToken(INVALID_TOKEN)).thenReturn(false);

        //执行过滤器
        jwtAuthFilter.doFilter(request, response, filterChain);

        //验证SecurityContextHolder是否被正确设置
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        //验证filterChain不再继续执行
        verify(filterChain,never()).doFilter(any(),any());
    }
    //✅ 测试 5：Token无效 - 返回401
    @Test
    void testDoFilter_InvalidSignatureToken() throws ServletException, IOException {
        //模拟请求头中带有错误Token
        request.addHeader("Authorization", "Bearer " + INVALID_TOKEN);

        //Mock Token验证失败
        when(jwtTokenProvider.validateToken(INVALID_TOKEN)).thenReturn(false);

        //执行过滤器
        jwtAuthFilter.doFilter(request, response, filterChain);
        //验证返回的响应状态码为401
        assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
        //验证FilterChain不再继续执行
        verify(filterChain,never()).doFilter(any(),any());
    }
}
