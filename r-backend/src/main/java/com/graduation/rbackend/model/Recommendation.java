package com.graduation.rbackend.model;


import jakarta.persistence.*;

/**
 * 推荐实体类，保存推荐算法的输出结果
 */
@Entity
@Table(name = "recommendations") // 数据库表名为 recommendations
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student; // 用户 ID（外键）

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course; // 课程 ID（外键）


    private Double score; // 推荐分数

    private String algorithmType;// 使用的推荐算法类型
    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }
}
