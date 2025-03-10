package com.graduation.rbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 课程实体类，映射数据库中的课程表
 */
@Entity
@Table(name = "courses") // 数据库表名为 courses
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;

    @JsonProperty("course_name")
    @Column(nullable = false) // 课程名称不能为空
    private String courseName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer credits; // 学分

    @Column(name="teacher_id", nullable = false)
    private Long teacherId; // 教师 ID（外键）


}

