package com.graduation.rbackend.model;

import jakarta.persistence.*;

/**
 * 用户实体类，映射数据库中的用户表
 */
@Entity
@Table(name = "users") // 数据库表名为 users
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;

    @Column(nullable = false, unique = true) // 用户名不能为空且唯一
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email; // 用户邮箱

    @Column(nullable = false)
    private String role; // 用户角色，例如学生、教师等


    public User() {

    }

    public User(Long id, String name, String email) {
    }

    public User(Long id, String name, String password, String email) {
    }

    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}