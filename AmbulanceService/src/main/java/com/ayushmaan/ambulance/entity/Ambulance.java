package com.ayushmaan.ambulance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ambulance")
public class Ambulance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private String ambulanceType;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private AmbulanceCategory category;
    private int available;
    private int inService;
}
