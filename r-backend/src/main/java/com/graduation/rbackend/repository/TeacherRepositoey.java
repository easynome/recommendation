package com.graduation.rbackend.repository;

import com.graduation.rbackend.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepositoey extends JpaRepository<Teacher, Long> {
    // 根据部门查询教师
    List<Teacher> findByDepartment(String department);
}
