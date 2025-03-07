package com.graduation.rbackend.repository;

import com.graduation.rbackend.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 存储推荐算法产生的推荐结果（如缓存用户推荐、历史推荐记录等）
 */
public interface RecommendationRepository extends JpaRepository<Recommendation,Long> {
    //根据学生ID查找推荐记录
    List<Recommendation> findByStudentId(Long studentId);
    //根据课程ID查找推荐记录
    List<Recommendation> findByCourseId(Long courseId);
}
