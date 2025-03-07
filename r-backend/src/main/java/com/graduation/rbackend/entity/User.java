//package com.graduation.rbackend.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
///**
// * 用户实体类，映射数据库中的用户表
// */
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//@Table(name = "users") // 数据库表名为 users
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)// 继承方式，单表继承
//@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)// 定义一个名为user_type的列，用于区分用户类型
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
//    private Long id;
//
//    @Column(nullable = false, unique = true) // 用户名不能为空且唯一
//    private String username;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Column(nullable = false)
//    private String email; // 用户邮箱
//
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)  // 存储为字符串,用户角色，例如"STUDENT,TEACHER,ADMIN"
//    private UserRole role;
//
//
//
//    // 仅 STUDENT 角色使用
//    private String major;
//    private Integer grade;
//
//    // 仅 TEACHER 角色使用
//    private String department;
//    private String position;
//
//    public enum UserRole {
//        ADMIN, STUDENT,TEACHER
//    }
//    // 用于 ADMIN 用户的构造器
//    public User(String username, String password, String email, UserRole role) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.role = role;
//    }
//
//    // 用于 STUDENT 用户的构造器
//    public User(String username, String password, String email, UserRole role, String major, Integer grade) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.role = role;
//        this.major = major;
//        this.grade = grade;
//    }
//    //用于 TEACHER 用户的构造器
//    public User(String username, String password, String email, UserRole role, String department, String position) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.role = role;
//        this.department = department;
//        this.position = position;
//    }
//
//}