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
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;

    @Column(name = "course_name", nullable = false) // 课程名称不能为空
    private String courseName;

    // ✅ 添加 `@JsonProperty` 在 `getter` 上，确保 JSON 数据正确解析
//    @JsonProperty("course_name")
//    public String getCourseName() {
//        return courseName;
//    }
//
//    @JsonProperty("course_name")
//    public void setCourseName(String courseName) {
//        this.courseName = courseName;
//    }
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer credits; // 学分

//    @JsonProperty("teacherId")
    @Column(name="teacher_id", nullable = false)
    private Long teacherId; // 教师 ID（外键）

    @Column(columnDefinition = "JSON")
    private String tags;  // ✅ 使用 `String` 而非 `List` 或 `Map`，避免 Jackson 解析失败


    @Column(name = "rating")
    private Float rating;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
}

