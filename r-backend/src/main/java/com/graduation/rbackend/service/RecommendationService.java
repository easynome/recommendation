//package com.graduation.recommendationbackend.service;
//
//import com.graduation.recommendationapi.dto.RecommendationDTO;
//
//import java.util.List;
//
///**
// * 实现学习推荐算法，生成个性化推荐结果
// */
//
//public class RecommendationService {
//    public List<RecommendationDTO> getRecommendations(Long userId){
//        //这里实现推荐算法，或者模拟数据
//        return List.of(new RecommendationDTO(1L,"Java Programming"),new RecommendationDTO(2L,"Data Structures"));
//    }
//}

package com.graduation.rbackend.service;


import com.graduation.rbackend.entity.Recommendation;
import com.graduation.rbackend.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现学习推荐算法，生成个性化推荐结果
 */
@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    //保存推荐记录
    public Recommendation addRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }
    //获取所有推荐记录
    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }
    //根据学生id查找推荐记录
    public List<Recommendation> findByStudentId(Long studentId) {
        return recommendationRepository.findByStudentId(studentId);
    }
    //根据课程id查找推荐记录
    public List<Recommendation> findByCourseId(Long courseId) {
        return recommendationRepository.findByCourseId(courseId);
    }
    //删除推荐记录
    public void deleteRecommendationById(Long id) {
        recommendationRepository.deleteById(id);
    }
//    public List<RecommendationDTO> getRecommendations(Long userId) {
//        // 这里实现推荐算法，或者模拟数据
//        return List.of(new RecommendationDTO(1L,"Java Programming"),
//                new RecommendationDTO(2L, "Data Structures"));
//    }
}
