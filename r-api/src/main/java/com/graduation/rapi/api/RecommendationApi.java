package com.graduation.rapi.api;

import com.graduation.rapi.dto.RecommendationResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


public interface RecommendationApi {

    @GetMapping("/recommendations")
    RecommendationResponseDTO getRecommendations(@RequestParam("userId") String userId);
}
