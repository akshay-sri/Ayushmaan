package com.ayushmaan.ai.controller;

import com.ayushmaan.ai.dto.AmbulanceRecommendationResponse;
import com.ayushmaan.ai.service.AIService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@Tag(name = "Ambulance Apis", description = "Operations related to ambulance")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService){
        this.aiService = aiService;
    }

    @PostMapping("/help")
    public ResponseEntity<AmbulanceRecommendationResponse> getInstantHelp(@RequestBody Map<String, String> request){
        String issue = request.get("issue");
        AmbulanceRecommendationResponse response = aiService.getInstantHelp(issue);
        return ResponseEntity.ok(response);
    }
}
