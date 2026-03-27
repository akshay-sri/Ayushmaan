package com.ayushmaan.ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class AIService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;

    public AIService(WebClient.Builder webclient){
        this.webClient = webclient.build();
    }

    public String getInstantHelp(String issue) {
        Map<String, Object> request = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[] {
                                Map.of("text", issue)
                        })
                }
        );

        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }
}
