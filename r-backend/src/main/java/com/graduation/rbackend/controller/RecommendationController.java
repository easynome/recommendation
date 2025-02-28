package com.graduation.rbackend.controller;

import com.graduation.rbackend.model.Recommendation;
import com.graduation.rbackend.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处理学习推荐相关的请求（如获取个性化推荐）
 */

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

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
    @PostMapping
    public List<Recommendation> addRecommendations(@RequestBody List<Recommendation> recommendations) {
        return recommendationService.saveRecommendations(recommendations);
    }
}
