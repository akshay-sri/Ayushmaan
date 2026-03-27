package com.ayushmaan.ambulance.repository;

import com.ayushmaan.ambulance.entity.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmbulanceRepository extends JpaRepository<Ambulance, Long> {
    Optional<Ambulance> findByAmbulanceType(String ambulanceType);
}
