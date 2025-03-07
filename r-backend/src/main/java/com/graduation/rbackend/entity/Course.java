package com.graduation.rbackend.entity;

import jakarta.persistence.*;

/**
 * 课程实体类，映射数据库中的课程表
 */
@Entity
@Table(name = "courses") // 数据库表名为 courses
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;

    @Column(nullable = false) // 课程名称不能为空
    private String courseName;

    @Column(nullable = false)
    private Integer credits; // 学分

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "teacher_id",nullable = false)
    private Teacher teacher; // 教师 ID（外键）

    public Course(Long id, String courseName, Integer credits, Teacher teacher) {
        this.id = id;
        this.courseName = courseName;
        this.credits = credits;
        this.teacher = teacher;
    }

    public Course() {
    }
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }


}

