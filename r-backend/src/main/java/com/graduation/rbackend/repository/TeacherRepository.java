package com.graduation.rbackend.repository;

import com.graduation.rbackend.entity.BaseUser;
import com.graduation.rbackend.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // 根据部门查询教师
    List<Teacher> findByDepartment(String department);

    Optional<Teacher> findByUsername(String username);
//    @Query("SELECT new com.graduation.rbackend.entity.BaseUser(t.id, t.username, t.role) FROM Teacher t WHERE t.username = :username")
//    Optional<BaseUser> findBaseUserByUsername(@Param("username") String username);

}
