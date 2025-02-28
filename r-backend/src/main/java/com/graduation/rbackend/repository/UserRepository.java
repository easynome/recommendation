package com.graduation.rbackend.repository;

import com.graduation.rbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 用户相关的数据操作（如存储用户、查询用户等）
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // 根据用户名查找用户
    Optional<User> findByUsername(String username);

    // 根据角色查找用户
    Optional<User> findByRole(String role);
}
