package com.graduation.rbackend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Student")
public class Student extends User {


    private String major;

    private Integer grade;

    public Student() {
        super();
    }


    // Getters and Setters



    public Student(Long id, String username, String password, String email, String major, Integer grade) {
        super(id, username, password, email); // 调用父类构造器初始化通用字段
        this.major = major;
        this.grade = grade;
    }


    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}


