package com.graduation.rbackend.service;

import com.graduation.rbackend.entity.Admin;
import com.graduation.rbackend.entity.Student;
import com.graduation.rbackend.entity.Teacher;
import com.graduation.rbackend.repository.AdminRepository;
import com.graduation.rbackend.repository.StudentRepository;
import com.graduation.rbackend.repository.TeacherRepository;
import com.graduation.rbackend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public String login(String username, String password) {
        // 🟠 查找用户
        Optional<Student> studentOpt = studentRepository.findByUsername(username);
        Optional<Teacher> teacherOpt = teacherRepository.findByUsername(username);
        Optional<Admin> adminOpt = adminRepository.findByUsername(username);

        // ✅ 登录验证逻辑
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            if (passwordEncoder.matches(password, student.getPassword())) {
                return jwtTokenProvider.generateToken(student.getUsername(), "STUDENT");
            }
            throw new BadCredentialsException("密码错误");
        }

        if (teacherOpt.isPresent()) {
            Teacher teacher = teacherOpt.get();
            if (passwordEncoder.matches(password, teacher.getPassword())) {
                return jwtTokenProvider.generateToken(teacher.getUsername(), "TEACHER");
            }
            throw new BadCredentialsException("密码错误");
        }

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (passwordEncoder.matches(password, admin.getPassword())) {
                return jwtTokenProvider.generateToken(admin.getUsername(), "ADMIN");
            }
            throw new BadCredentialsException("密码错误");
        }

        throw new BadCredentialsException("用户不存在");
    }
}

