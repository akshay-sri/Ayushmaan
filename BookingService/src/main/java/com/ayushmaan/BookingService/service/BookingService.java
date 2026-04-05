package com.ayushmaan.BookingService.service;

import com.ayushmaan.BookingService.Dto.BookingRequest;
import com.ayushmaan.BookingService.entity.Booking;
import com.ayushmaan.BookingService.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    public BookingService(BookingRepository bookingRepository, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
    }

    public String book(BookingRequest request, String email){
        Booking newBooking = new Booking();
        newBooking.setUserEmail(email);
        this.modelMapper.map(request, newBooking);
        bookingRepository.save(newBooking);
        return "Ambulance booked successfully!";
    }
}
