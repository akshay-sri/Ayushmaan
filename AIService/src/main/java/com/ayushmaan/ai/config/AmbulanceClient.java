package com.ayushmaan.ai.config;

import com.ayushmaan.ai.dto.AmbulanceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Component
public class AmbulanceClient {

    private final WebClient webClient;;
    private String endpoint;

    public AmbulanceClient(
            WebClient.Builder builder,
            @Value("${com.ayushmaan.ai.ambulance-service.url}") String baseUrl,
            @Value("${com.ayushmaan.ai.ambulance-service.endpoint}") String endpoint) {

        this.webClient = builder.baseUrl(baseUrl).build();
        this.endpoint = endpoint;
    }

    public List<String> getAmbulances() {

        System.out.println("Fetching ambulance types from ambulance service...");
        List<AmbulanceDto> response = webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<AmbulanceDto>>() {})
                .block();

        if (response == null) {
            return List.of();
        }

        return response.stream()
                .map(x->x.getAmbulanceType() + " - " + x.getDescription())
                .toList();
    }
}