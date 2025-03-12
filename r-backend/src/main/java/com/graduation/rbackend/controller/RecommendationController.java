package com.graduation.rbackend.controller;

import com.graduation.rbackend.entity.Recommendation;
import com.graduation.rbackend.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * 处理学习推荐相关的请求（如获取个性化推荐）
 */

@Slf4j
@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {


    private final RecommendationService recommendationService;

    // 获取所有推荐
    @GetMapping
    public List<Recommendation> getAllRecommendations() {
        return recommendationService.getAllRecommendations();
    }

    // 根据学生id获取推荐
    @GetMapping("/student/{id}")
    public List<Recommendation> findByStudentId(@PathVariable Long id) {
        return recommendationService.findByStudentId(id);
    }

    // 根据课程id获取推荐
    @GetMapping("/course/{id}")
    public List<Recommendation> findByCourseId(@PathVariable Long id) {
        return recommendationService.findByCourseId(id);
    }

    // 删除推荐
    @DeleteMapping("/{id}")
    public void deleteRecommendationById(@PathVariable Long id) {
        recommendationService.deleteRecommendationById(id);
    }

    // 添加推荐
    @PostMapping("/add")
    public ResponseEntity<Recommendation> addRecommendation(@RequestBody Recommendation recommendation) {
        log.info("接收到的 JSON 数据: {}", recommendation);
        if(recommendation.getStudent() == null || recommendation.getCourse() == null){
            log.warn("❗ studentId 或 courseId 为空，JSON 数据未正确解析");
            return ResponseEntity.badRequest().build();
        }
        recommendation.setRecommendationDate(new Timestamp(System.currentTimeMillis()));
        Recommendation saveRecommendation = recommendationService.addRecommendation(recommendation);
        return ResponseEntity.ok(saveRecommendation);
    }
}
