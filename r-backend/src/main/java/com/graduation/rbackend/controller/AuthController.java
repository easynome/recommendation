package com.graduation.rbackend.controller;


import com.graduation.rbackend.entity.Admin;
import com.graduation.rbackend.entity.Student;
import com.graduation.rbackend.entity.Teacher;
import com.graduation.rbackend.repository.AdminRepository;
import com.graduation.rbackend.repository.StudentRepository;
import com.graduation.rbackend.repository.TeacherRepository;
import com.graduation.rbackend.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Map<String,String> loginRequest){
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        log.info("🟠 正在尝试登录，用户名: {}", username);

        //查找用户
        Optional<Student> studentOpt= studentRepository.findByUsername(username);
        Optional<Teacher> teacherOpt = teacherRepository.findByUsername(username);
        Optional<Admin> adminOpt = adminRepository.findByUsername(username);

        log.info("✅ Student 数据: {}", studentOpt.isPresent());
        log.info("✅ Teacher 数据: {}", teacherOpt.isPresent());
        log.info("✅ Admin 数据: {}", adminOpt.isPresent());

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            if (passwordEncoder.matches(password, student.getPassword())) {
                String token = jwtTokenProvider.generateToken(student.getUsername(),"STUDENT");
                return ResponseEntity.ok(Map.of("token", token));
            }
        }
        if (teacherOpt.isPresent()) {
            Teacher teacher = teacherOpt.get();
            if (passwordEncoder.matches(password, teacher.getPassword())) {
                String token = jwtTokenProvider.generateToken(teacher.getUsername(),"TEACHER");
                return ResponseEntity.ok(Map.of("token", token));
            }
        }
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (passwordEncoder.matches(password, admin.getPassword())) {
                String token = jwtTokenProvider.generateToken(admin.getUsername(),"ADMIN");
                return ResponseEntity.ok(Map.of("token", token));
            }
        }
        return ResponseEntity.ok("登录失败");
    }

    //公共接口（不需要认证）
    @GetMapping("/public")
    public String publicEndpoint() {
        return "这是一个公共接口，任何人都可以访问";
    }

    // 受保护接口（需要认证）
    @GetMapping("/protected")
    public ResponseEntity<?> protectedEndpoint() {
        return ResponseEntity.ok("🔒 这是一个受保护接口，您已通过认证！");
    }


}
