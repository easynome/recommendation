package com.graduation.rbackend.service;

import com.graduation.rbackend.entity.BaseUser;
import com.graduation.rbackend.repository.AdminRepository;
import com.graduation.rbackend.repository.StudentRepository;
import com.graduation.rbackend.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 依次查询角色表，直接返回完整用户信息
        return studentRepository.findByUsername(username)
                .map(UserDetailsImpl::new)
                .orElseGet(() -> teacherRepository.findByUsername(username)
                        .map(UserDetailsImpl::new)
                        .orElseGet(() -> adminRepository.findByUsername(username)
                                .map(UserDetailsImpl::new)
                                .orElseThrow(() -> new UsernameNotFoundException("用户未找到: " + username))));
    }
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 先查询 username 存在于哪个角色表
//        BaseUser baseUser = studentRepository.findBaseUserByUsername(username)
//                .orElseGet(() -> teacherRepository.findBaseUserByUsername(username)
//                        .orElseGet(() -> adminRepository.findBaseUserByUsername(username)
//                                .orElseThrow(() -> new UsernameNotFoundException("用户未找到: " + username))));
//
//        // 根据用户角色查询具体表
//        return switch (baseUser.getRole()) {
//            case STUDENT -> studentRepository.findByUsername(username)
//                    .map(UserDetailsImpl::new)
//                    .orElseThrow(() -> new UsernameNotFoundException("学生信息未找到: " + username));
//
//            case TEACHER -> teacherRepository.findByUsername(username)
//                    .map(UserDetailsImpl::new)
//                    .orElseThrow(() -> new UsernameNotFoundException("教师信息未找到: " + username));
//
//            case ADMIN -> adminRepository.findByUsername(username)
//                    .map(UserDetailsImpl::new)
//                    .orElseThrow(() -> new UsernameNotFoundException("管理员信息未找到: " + username));
//        };
//    }

}