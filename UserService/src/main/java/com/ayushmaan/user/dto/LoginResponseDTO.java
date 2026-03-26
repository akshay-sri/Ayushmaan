package com.ayushmaan.user.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String accessToken;

    public LoginResponseDTO(String token) {
        this.accessToken = token;
    }
}
