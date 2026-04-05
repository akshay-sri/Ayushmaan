package com.ayushmaan.BookingService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "booking")
public class Booking {
    @Id
    private UUID id;
    private String ambulanceType;
    private double pickupLocation;
    private double dropLocation;
    private String userEmail;
}
