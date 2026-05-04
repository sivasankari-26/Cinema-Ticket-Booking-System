package com.cinema.service;

import com.cinema.model.Booking;
import com.cinema.model.Seat;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    // Lucky seat index (0-based): index 7 = B3
    private static final int LUCKY_SEAT_INDEX = 7;

    private static final String[] SEAT_LABELS = {
        "A1","A2","A3","A4","A5",
        "B1","B2","B3","B4","B5",
        "C1","C2","C3","C4","C5",
        "D1","D2","D3","D4","D5",
        "E1","E2","E3","E4","E5"
    };

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Book MULTIPLE seats at once - returns true if any lucky seat is included
    public boolean bookSeats(String showId, List<String> seatNumbers,
                             String customerName, String phone, String paymentType) {
        boolean luckyFound = false;

        for (String seatNumber : seatNumbers) {
            Optional<Seat> seatOpt = seatRepository.findByShowIdAndSeatNumber(showId, seatNumber);
            if (seatOpt.isEmpty()) continue;

            Seat seat = seatOpt.get();
            if (!seat.isStatus()) continue; // already booked, skip

            // Mark seat as BOOKED
            seat.setStatus(false);
            seatRepository.save(seat);

            // Save one booking record per seat
            Booking booking = new Booking();
            booking.setShowId(showId);
            booking.setSeatNumber(seatNumber);
            booking.setCustomerName(customerName);
            booking.setPhone(phone);
            booking.setPaymentType(paymentType);
            bookingRepository.save(booking);

            // Check lucky seat
            if (isLuckySeat(seatNumber)) {
                luckyFound = true;
            }
        }

        return luckyFound;
    }

    public boolean isLuckySeat(String seatNumber) {
        for (int i = 0; i < SEAT_LABELS.length; i++) {
            if (SEAT_LABELS[i].equals(seatNumber) && i == LUCKY_SEAT_INDEX) {
                return true;
            }
        }
        return false;
    }

    public void deleteAll() {
        bookingRepository.deleteAll();
    }
}
