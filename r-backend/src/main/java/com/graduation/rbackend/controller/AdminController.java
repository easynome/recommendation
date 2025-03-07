package com.graduation.rbackend.controller;

import com.graduation.rbackend.entity.Admin;
import com.graduation.rbackend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/{username}")
    public ResponseEntity<Admin> getAdminByUsername(@PathVariable String username) {
        return adminService.getAdminByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }
}
