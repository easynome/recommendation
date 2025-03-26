package com.graduation.rbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.*;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 课程实体类，映射数据库中的课程表
 */
@AllArgsConstructor
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
    @NotNull(message = "课程名称不能为空")
    @Size(min = 1, max = 255, message = "课程名称长度应在 1 到 255 之间")
    @JsonProperty("course_name")  // 映射 JSON 字段
    private String courseName;


    @Column(nullable = false)
    @NotNull(message = "课程描述不能为空")
    @Size(min = 1, max = 255, message = "课程描述长度应在 1 到 255 之间")
    private String description;

    @Column(nullable = false)
    @Min(value = 1, message = "学分必须大于 0")
    private Integer credits; // 学分

    @JsonProperty("teacherId")
    @Column(name = "teacher_id", nullable = false)
    @NotNull(message = "教师 ID 不能为空")
    private Long teacherId; // 教师 ID（外键）

    @Column(columnDefinition = "JSON")
    @JsonProperty("tags")
    @NotNull(message = "课程标签不能为空")
    private String tags;  // ✅ 使用 `String` 而非 `List` 或 `Map`，避免 Jackson 解析失败


    @Column(name = "rating")
    @Min(value = 0, message = "评分不能小于 0")
    private Float rating;

    @Column(name = "review_count")
    @Min(value = 0, message = "评论数量不能小于 0")
    private Integer reviewCount;

    @Column(name = "thumbnail_url")
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 创建时间

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 更新时间


    // 在实体保存前自动设置创建时间和更新时间
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    // 在实体更新前自动设置更新时间
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    public Course(Long id, String courseName, String description, Integer credits, Long teacherId, String tags, Float rating, Integer reviewCount, String thumbnailUrl) {
        this.id = id;
        this.courseName = courseName;
        this.description = description;
        this.credits = credits;
        this.teacherId = teacherId;
        this.tags = tags;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.thumbnailUrl = thumbnailUrl;
    }
}

