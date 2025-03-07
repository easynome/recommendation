package com.graduation.rbackend.service;

import com.graduation.rbackend.entity.Admin;
import com.graduation.rbackend.entity.BaseUser;
import com.graduation.rbackend.entity.Student;
import com.graduation.rbackend.entity.Teacher;
import com.graduation.rbackend.repository.AdminRepository;
import com.graduation.rbackend.repository.StudentRepository;
import com.graduation.rbackend.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {
    @Mock  // 模拟数据库
    private StudentRepository studentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks  // 自动注入 Mock 依赖
    private UserDetailsServiceImpl userDetailsService;

    private Student testStudent;
    private Teacher testTeacher;
    private Admin testAdmin;
    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setUsername("student1");
        testStudent.setPassword("password");
        testStudent.setEmail("student1@example.com");
        testStudent.setRole(BaseUser.UserRole.STUDENT);
        testStudent.setMajor("Computer Science");
        testStudent.setGrade(3);

        testTeacher = new Teacher();
        testTeacher.setUsername("teacher1");
        testTeacher.setPassword("password");
        testTeacher.setEmail("teacher1@example.com");
        testTeacher.setRole(BaseUser.UserRole.TEACHER);
        testTeacher.setDepartment("Mathematics");
        testTeacher.setPosition("Professor");

        testAdmin = new Admin();
        testAdmin.setUsername("admin1");
        testAdmin.setPassword("password");
        testAdmin.setEmail("admin1@example.com");
        testAdmin.setRole(BaseUser.UserRole.ADMIN);
    }
    @Test
    void testLoadUserByUsername_StudentFound() {
        when(studentRepository.findByUsername("student1")).thenReturn(Optional.of(testStudent));

        UserDetails userDetails = userDetailsService.loadUserByUsername("student1");

        assertNotNull(userDetails);
        assertEquals("student1", userDetails.getUsername());
        assertEquals("ROLE_STUDENT", userDetails.getAuthorities().iterator().next().getAuthority());

        verify(studentRepository, times(1)).findByUsername("student1");
        verifyNoInteractions(teacherRepository, adminRepository);
    }
    @Test
    void testLoadUserByUsername_AdminFound() {
        when(adminRepository.findByUsername("admin1")).thenReturn(Optional.of(testAdmin));
        when(teacherRepository.findByUsername("admin1")).thenReturn(Optional.empty());
        // 模拟 adminRepository 返回管理员对象
        when(adminRepository.findByUsername("admin1")).thenReturn(Optional.of(testAdmin));

        UserDetails userDetails = userDetailsService.loadUserByUsername("admin1");


        assertNotNull(userDetails);
        assertEquals("admin1", userDetails.getUsername());
        assertEquals("ROLE_ADMIN", userDetails.getAuthorities().iterator().next().getAuthority());

        // 验证与 studentRepository、teacherRepository 和 adminRepository 的交互
        verify(studentRepository, times(1)).findByUsername("admin1");
        verify(teacherRepository, times(1)).findByUsername("admin1");
        verify(adminRepository, times(1)).findByUsername("admin1");

    }
    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(studentRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        when(teacherRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        when(adminRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername("unknown")
        );

        assertEquals("User not foundunknown", exception.getMessage());

        verify(studentRepository, times(1)).findByUsername("unknown");
        verify(teacherRepository, times(1)).findByUsername("unknown");
        verify(adminRepository, times(1)).findByUsername("unknown");
    }
}
