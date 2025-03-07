package com.graduation.rbackend.repository;

import com.graduation.rbackend.entity.BaseUser;
import com.graduation.rbackend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByMajor(String Major);
    Optional<Student> findByUsername(String username);  // 按用户名查找学生

//    @Query("SELECT new com.graduation.rbackend.entity.BaseUser(s.id, s.username, s.role) FROM Student s WHERE s.username = :username")
//    Optional<BaseUser> findBaseUserByUsername(@Param("username") String username);

    //findById(Long id) 已经由 JpaRepository 提供。
    //save(Student student) 用于新增或更新学生。
    //deleteById(Long id) 删除学生。
}
