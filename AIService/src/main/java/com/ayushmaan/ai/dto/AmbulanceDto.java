package com.ayushmaan.ai.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmbulanceDto {
    private int quantity;
    private String ambulanceType;
    private String category;
    private int available;
    private int inService;
    private String description;
}
