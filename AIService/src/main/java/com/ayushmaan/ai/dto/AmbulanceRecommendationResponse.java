package com.ayushmaan.ai.dto;

import java.util.List;

public class AmbulanceRecommendationResponse {

    private String message;
    private List<String> recommendedAmbulances;

    public AmbulanceRecommendationResponse(String message, List<String> recommendedAmbulances) {
        this.message = message;
        this.recommendedAmbulances = recommendedAmbulances;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getRecommendedAmbulances() {
        return recommendedAmbulances;
    }
}