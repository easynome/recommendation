package com.graduation.rbackend.service;

import com.graduation.rbackend.entity.Admin;
import com.graduation.rbackend.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }


    public Admin addAdmin(Admin admin) {
        // ➤ 密码加密
        String encodedPassword =passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);

        log.info("✅ 管理员密码已加密: {}", encodedPassword);

        // ➤ 保存到数据库
        return adminRepository.save(admin);
    }
}
