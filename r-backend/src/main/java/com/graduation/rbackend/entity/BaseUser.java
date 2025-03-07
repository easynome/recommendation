package com.graduation.rbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass  // 关键注解：不生成表，仅继承字段
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;  // 角色枚举：ADMIN, STUDENT, TEACHER

    public enum UserRole {
        ADMIN,
        STUDENT,
        TEACHER
    }
    public BaseUser(Long id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}