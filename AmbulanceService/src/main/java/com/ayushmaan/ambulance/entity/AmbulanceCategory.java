package com.ayushmaan.ambulance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ambulance_category")
public class AmbulanceCategory {
    @Id
    @GeneratedValue
    private Long id;
    private String categoryName;
    private String description;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Ambulance> ambulanceTypes = new ArrayList<>();
}
