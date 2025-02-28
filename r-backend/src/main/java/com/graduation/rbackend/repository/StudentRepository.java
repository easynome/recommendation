package com.graduation.rbackend.repository;

import com.graduation.rbackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByMajor(String Major);
}
