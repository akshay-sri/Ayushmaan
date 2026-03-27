package com.ayushmaan.ambulance.repository;

import com.ayushmaan.ambulance.entity.AmbulanceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmbulanceCategoryRepository extends JpaRepository<AmbulanceCategory, Long> {
    Optional<AmbulanceCategory> findByCategoryName(String category);
}
