package com.graduation.rbackend.controller;

import com.graduation.rbackend.entity.Admin;
import com.graduation.rbackend.service.AdminService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;


    // 根据用户名获取管理员信息
    @GetMapping("/{username}")
    public ResponseEntity<Admin> getAdminByUsername(@PathVariable String username) {
        return adminService.getAdminByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    //注册管理员
//    @PostMapping("/register")
//    public ResponseEntity<?> register() {

    // 添加管理员
    @PostMapping
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        log.info("接收到的JSON数据：{}",admin);
        Admin savedAdmin = adminService.addAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }
}
