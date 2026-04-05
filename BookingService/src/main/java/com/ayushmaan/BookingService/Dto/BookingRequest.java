package com.ayushmaan.BookingService.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingRequest {
    private String ambulanceType;
    private double pickupLocation;
    private double dropLocation;
}
