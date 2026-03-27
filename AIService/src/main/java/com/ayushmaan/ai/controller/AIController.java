package com.ayushmaan.ai.controller;

import com.ayushmaan.ai.service.AIService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/ai")
public class AIController {

    private final AIService aiService;

    @PostMapping("/help")
    public ResponseEntity<String> getInstantHelp(@RequestBody Map<String, String> request){
        String issue = request.get("issue");
        String answer = aiService.getInstantHelp(issue);
        return ResponseEntity.ok(answer);
    }
}
