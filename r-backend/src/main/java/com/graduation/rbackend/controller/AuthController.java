package com.graduation.rbackend.controller;


import com.graduation.rbackend.dto.LoginRequestDTO;

import com.graduation.rbackend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody  LoginRequestDTO loginRequest) {
        if (loginRequest.getUsername().isBlank()) {
            return ResponseEntity.status(401).body(Map.of("error", "用户名不能为空"));
        }

        if (loginRequest.getPassword().isBlank()) {
            return ResponseEntity.status(401).body(Map.of("error", "密码不能为空"));
        }
        try {
            String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            log.info("✅ 用户 {} 登录成功", loginRequest.getUsername());  // 登录成功记录日志
            return ResponseEntity.ok(Map.of("token", token));
        } catch (BadCredentialsException e) {
            log.warn("❗ 登录失败: {}", e.getMessage());
            return ResponseEntity.status(401).body(Map.of("error", "用户名或密码错误"));
        }
    }

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        String content = "这是一个公共接口，任何人都可以访问";
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")).body(content);
    }

    @GetMapping("/protected")
    public ResponseEntity<?> protectedEndpoint() {
        String content="🔒 这是一个受保护接口，您已通过认证！";
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")).body(content);
    }
}