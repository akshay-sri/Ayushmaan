package com.ayushmaan.BookingService.repository;

import com.ayushmaan.BookingService.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

}
