package com.graduation.rbackend.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        System.out.println("加密后的密码："+encoder.encode("password123"));
    }
}
