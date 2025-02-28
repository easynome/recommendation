//package com.graduation.recommendationapi.dto;
//
//public class RecommendationDTO {
//    public RecommendationDTO(long l, String javaProgramming) {
//    }
//}
package com.graduation.rapi.dto;

public class RecommendationDTO {

    private long id;
    private String courseName;

    // 构造函数
    public RecommendationDTO(long id, String courseName) {
        this.id = id;
        this.courseName = courseName;
    }

    // Getter 和 Setter 方法
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
