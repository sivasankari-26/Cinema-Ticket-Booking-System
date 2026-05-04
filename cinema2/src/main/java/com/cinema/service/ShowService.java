package com.cinema.service;

import com.cinema.model.Seat;
import com.cinema.model.Show;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    private static final String[] SEAT_LABELS = {
        "A1","A2","A3","A4","A5",
        "B1","B2","B3","B4","B5",
        "C1","C2","C3","C4","C5",
        "D1","D2","D3","D4","D5",
        "E1","E2","E3","E4","E5"
    };

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> getShowsByMovieId(String movieId) {
        return showRepository.findByMovieId(movieId);
    }

    public Optional<Show> getShowById(String id) {
        return showRepository.findById(id);
    }

    public String addShow(Show show) {
        List<Show> conflicts = showRepository.findByShowTimeAndScreenNumber(show.getShowTime(), show.getScreenNumber());
        if (!conflicts.isEmpty()) {
            return "CONFLICT: Screen " + show.getScreenNumber() + " already has a show at " + show.getShowTime() + ". Please choose a different time or screen.";
        }
        showRepository.save(show);
        List<Seat> seats = new ArrayList<>();
        for (String label : SEAT_LABELS) {
            seats.add(new Seat(show.getId(), label));
        }
        seatRepository.saveAll(seats);
        return null;
    }

    public List<Seat> getSeatsForShow(String showId) {
        return seatRepository.findByShowId(showId);
    }

    public void deleteAll() {
        seatRepository.deleteAll();
        showRepository.deleteAll();
    }
}
