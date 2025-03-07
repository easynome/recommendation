package com.graduation.rbackend.repository;

import com.graduation.rbackend.entity.Admin;
import com.graduation.rbackend.entity.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username); // 按用户名查找管理员
//    @Query("SELECT new com.graduation.rbackend.entity.BaseUser(a.id, a.username, a.role) FROM Admin a WHERE a.username = :username")
//    Optional<BaseUser> findBaseUserByUsername(@Param("username") String username);

}
