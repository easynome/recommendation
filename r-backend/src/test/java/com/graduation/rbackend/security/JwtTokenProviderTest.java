package com.graduation.rbackend.security;

import com.graduation.rbackend.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import javax.crypto.SecretKey;
import java.util.Date;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtTokenProviderTest {
    private JwtTokenProvider jwtTokenProvider;
    private final SecretKey secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(secretKey);//使用正确的密钥初始化
    }

    @Test
    void testGenerateToken() {
        String username = "admin1";
        String role = "ADMIN";
        String token = jwtTokenProvider.generateToken(username, role);
        assertThat(token).isNotNull();
    }

    // 测试获取用户详情
    @Test
    void testGetUserDetail_Success(){
        String username = "admin1";
        String role = "ADMIN";
        String token = jwtTokenProvider.generateToken(username, role);
        User userDetails= (User) jwtTokenProvider.getUserDetails(token);
        assertThat(userDetails.getUsername()).isEqualTo(username);
        assertThat(userDetails.getAuthorities())
                .extracting("authority")
                .contains("ROLE_ADMIN");

    }

    //测试无效Token
    @Test
    void testValidateToken_ValidToken(){
        String username = "admin1";
        String role = "ADMIN";
        String token = jwtTokenProvider.generateToken(username, role);
        boolean isValid = jwtTokenProvider.validateToken(token);
        assertThat(isValid).isTrue();
    }

    //测试过期Token
    @Test
    void testValidateToken_ExpiredToken(){

        long expirationTime = -1000L;//设置过期时间
        String expiredToken=io.jsonwebtoken.Jwts.builder()
                .setSubject("admin1")
                .claim("role","ADMIN")
                .setIssuedAt(new Date())
                .setExpiration((new Date(System.currentTimeMillis()+expirationTime)))
                .signWith(secretKey)
                .compact();
        assertThat(jwtTokenProvider.validateToken(expiredToken)).isFalse();
    }
    //测试无效签名
    @Test
    void testValidateToken_InvalidSignature() {
        String validToken = jwtTokenProvider.generateToken("admin1", "ADMIN");
        String invalidToken = validToken.substring(0, validToken.length() - 2) + "XX"; // 篡改签名

        assertThat(jwtTokenProvider.validateToken(invalidToken)).isFalse();
    }

    // 测试获取认证信息
    @Test
    void testGetAuthentication_Success() {
        String token = jwtTokenProvider.generateToken("teacher1", "TEACHER");

        Authentication auth = jwtTokenProvider.getAuthentication(token);
        assertThat(auth.getName()).isEqualTo("teacher1");
        assertThat(auth.getAuthorities()).extracting("authority").contains("ROLE_TEACHER");
    }

    // 测试获取Claims
    @Test
    void testGetClaims_InvalidToken_ShouldThrowException() {
        String invalidToken = "invalidTokenExample";

        assertThrows(Exception.class, () -> jwtTokenProvider.getClaims(invalidToken));
    }
}
