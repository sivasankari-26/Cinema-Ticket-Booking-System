package com.cinema.repository;

import com.cinema.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, String> {
    List<Show> findByMovieId(String movieId);
    List<Show> findByShowTimeAndScreenNumber(String showTime, int screenNumber);
}
