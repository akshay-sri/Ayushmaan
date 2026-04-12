package com.ayushmaan.BookingService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
        name = "booking",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_active_booking",
                        columnNames = {"user_email", "pickup_location", "drop_location", "status"}
                )
        }
)
public class Booking {

    @Id
    private String id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "pickup_location")
    private String pickupLocation;

    @Column(name = "drop_location")
    private String dropLocation;

    private String ambulanceType;

    private String status; // CONFIRMED, COMPLETED, CANCELLED

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
}