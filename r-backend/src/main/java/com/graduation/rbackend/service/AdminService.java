package com.graduation.rbackend.service;

import com.graduation.rbackend.entity.Admin;
import com.graduation.rbackend.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
}
