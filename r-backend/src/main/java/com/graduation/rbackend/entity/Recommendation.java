package com.graduation.rbackend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

/**
 * 推荐实体类，保存推荐算法的输出结果
 */
//@Getter
//@Setter
@Entity
@NoArgsConstructor
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

    @Column(nullable = false)
    private Double score; // 推荐分数

    @Column(name="algorithm_type",nullable = false)
    private String algorithmType;// 使用的推荐算法类型

    @Column(name = "recommendation_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp recommendationDate; // 推荐生成时间

    @Column(name = "feedback")
    private String feedback; // 用户反馈

    @Column(name = "feedback_time")
    private Timestamp feedbackTime; // 反馈时间

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

    public Timestamp getRecommendationDate() {
        return recommendationDate;
    }

    public void setRecommendationDate(Timestamp recommendationDate) {
        this.recommendationDate = recommendationDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Timestamp getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Timestamp feedbackTime) {
        this.feedbackTime = feedbackTime;
    }


}
