package com.graduation.rbackend.model;

import com.graduation.rbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;





public class demo {
    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
        return args -> {
            // 检查是否已经存在超级管理员
            if (userRepository.findByUsername("admin") == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder().encode("admin123"));  // 加密密码
                admin.setRole("ADMIN");  // 标记为管理员
                userRepository.save(admin);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}