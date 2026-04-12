package com.ayushmaan.BookingService.controller;

import com.ayushmaan.BookingService.Dto.BookingRequest;
import com.ayushmaan.BookingService.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBooking(
            @RequestBody BookingRequest request,
            @RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(bookingService.book(request, email));
    }
}
