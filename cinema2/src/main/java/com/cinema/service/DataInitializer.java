package com.cinema.service;

import com.cinema.model.Movie;
import com.cinema.model.Show;
import com.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowService showService;

    @Override
    public void run(String... args) {
        // Only seed data if no movies exist
        if (movieRepository.count() == 0) {
            Movie m1 = new Movie("M1", "Leo", "Action");
            Movie m2 = new Movie("M2", "Jawan", "Thriller");
            movieRepository.save(m1);
            movieRepository.save(m2);

            Show s1 = new Show("SH1", "10:00 AM", "M1", 1);
            Show s2 = new Show("SH2", "03:00 PM", "M2", 1);
            showService.addShow(s1);
            showService.addShow(s2);

            System.out.println("Sample data initialized!");
        }
    }
}
