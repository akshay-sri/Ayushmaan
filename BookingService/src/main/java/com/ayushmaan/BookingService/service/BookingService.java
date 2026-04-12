package com.ayushmaan.BookingService.service;

import com.ayushmaan.BookingService.Dto.BookingRequest;
import com.ayushmaan.BookingService.entity.Booking;
import com.ayushmaan.BookingService.kafka.BookingConfirmedEvent;
import com.ayushmaan.BookingService.kafka.KafkaProducerService;
import com.ayushmaan.BookingService.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class BookingService {
    private static final String LOCK_PREFIX = "LOCK:";
    private static final String BOOKED_PREFIX = "BOOKED:";
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final KafkaProducerService producer;

    public BookingService(BookingRepository bookingRepository, ModelMapper modelMapper, RedisTemplate<String, String> redisTemplate, KafkaProducerService producer) {
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
        this.redisTemplate = redisTemplate;
        this.producer = producer;
    }

    public String book(BookingRequest request, String email){
        String key = buildKey(request, email);
        String lockKey = LOCK_PREFIX + key;
        String bookedKey = BOOKED_PREFIX + key;

        // ⚡ Step 1: Fast rejection (no DB hit)
        if (Boolean.TRUE.equals(redisTemplate.hasKey(bookedKey))) {
            throw new RuntimeException("Duplicate booking not allowed");
        }

        // 🔒 Step 2: Distributed lock
        Boolean lockAcquired = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, "LOCK", Duration.ofSeconds(10));

        if (Boolean.FALSE.equals(lockAcquired)) {
            throw new RuntimeException("Booking already in progress");
        }

        try {
            // 🚀 Step 3: Direct DB insert (NO SELECT)
            Booking newBooking = new Booking();
            newBooking.setStatus("CONFIRMED");
            newBooking.setUserEmail(email);
            this.modelMapper.map(request, newBooking);
            Booking savedBooking = bookingRepository.save(newBooking);
            BookingConfirmedEvent bookingConfirmedEvent =
                    this.modelMapper.map(savedBooking, BookingConfirmedEvent.class);
            producer.publishBookingConfirmed(bookingConfirmedEvent);
            // ⚡ Step 4: Cache result
            redisTemplate.opsForValue()
                    .set(bookedKey, "BOOKED", Duration.ofMinutes(30));

            return "Ambulance booked successfully!";

        } catch (DataIntegrityViolationException ex) {
            // 🔒 Step 5: DB constraint fallback
            throw new RuntimeException("Duplicate booking detected");
        } finally {
            // 🔓 Step 6: Always release lock
            redisTemplate.delete(lockKey);
        }
    }

    public void completeBooking(String bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        BookingRequest request = this.modelMapper.map(booking, BookingRequest.class);

        booking.setStatus("COMPLETED");
        bookingRepository.save(booking);

        // 🔥 VERY IMPORTANT
        redisTemplate.delete("BOOKED:" + buildKey(request, booking.getUserEmail()));
    }

    private String buildKey(BookingRequest booking, String email) {
        return email.toLowerCase().trim() + ":" +
                booking.getPickupLocation().trim() + ":" +
                booking.getDropLocation().trim();
    }
}
