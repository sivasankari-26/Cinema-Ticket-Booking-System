package com.cinema.repository;

import com.cinema.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByShowId(String showId);
    Optional<Seat> findByShowIdAndSeatNumber(String showId, String seatNumber);
    void deleteByShowId(String showId);
}
