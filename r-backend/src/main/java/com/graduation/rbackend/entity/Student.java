package com.graduation.rbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "students")
public class Student extends BaseUser {


    private String major;

    private Integer grade;
//
//    // 直接通过继承的 id 关联到 users 表，无需额外字段
//    @Column(name = "user_id", unique = true)
//    private Long userId;  // 与 users.id 一对一映射
}




