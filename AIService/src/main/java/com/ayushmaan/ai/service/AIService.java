package com.ayushmaan.ai.service;

import com.ayushmaan.ai.config.AmbulanceClient;
import com.ayushmaan.ai.dto.AmbulanceRecommendationResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AIService {

    private final ChatClient chatClient;
    private final AmbulanceClient ambulanceClient;

    public AIService(ChatClient.Builder builder, AmbulanceClient ambulanceClient){
        this.chatClient = builder.build();
        this.ambulanceClient = ambulanceClient;
    }

    @Cacheable(value = "ai-recommendations", key = "#issue")
    public AmbulanceRecommendationResponse getInstantHelp(String issue){
        List<String> ambulanceTypes = getAmbulances();

        String prompt = """
                You are an emergency response assistant.

                User emergency description:
                %s

                Available ambulance types:
                %s

                Instructions:
                - Select the most relevant ambulance types.
                - Do not invent new ambulance types.
                - Return ONLY ambulance names separated by comma.
                - Do NOT return JSON.

                Example:
                ALS, BLS
                """.formatted(issue, String.join(", ", ambulanceTypes));
        String aiResponse = chatClient.prompt(prompt).call().content();
        List<String> recommendedTypes = Arrays.stream(aiResponse.split(","))
                .map(String::trim)
                .toList();

        return new AmbulanceRecommendationResponse("Here are the ambulances based on your issue", recommendedTypes);

    }

    @Cacheable(value = "ambulances", key = "'all'")
    public List<String> getAmbulances() {
        return ambulanceClient.getAmbulances();
    }
}
