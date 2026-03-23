package com.ayushmaan.user.dto;

import lombok.Getter;

@Getter
public class SignUpRequestDTO {
    private String email;
    private String password;
    private String name;
}
