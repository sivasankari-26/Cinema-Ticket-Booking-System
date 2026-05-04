package com.cinema.service;

import com.cinema.model.Movie;
import com.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PosterService posterService;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(String id) {
        return movieRepository.findById(id);
    }

    public void addMovie(Movie movie) {
        // Auto-fetch poster by movie title
        String poster = posterService.fetchPoster(movie.getTitle());
        movie.setPosterUrl(poster);
        movieRepository.save(movie);
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }
}
