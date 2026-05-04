package com.cinema.controller;

import com.cinema.model.Booking;
import com.cinema.model.Movie;
import com.cinema.model.Show;
import com.cinema.service.BookingService;
import com.cinema.service.MovieService;
import com.cinema.service.ShowService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowService showService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("")
    public String adminPage(HttpSession session, Model model) {
        if (!"admin".equals(session.getAttribute("role"))) return "redirect:/login";
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("shows", showService.getAllShows());
        return "admin";
    }

    @PostMapping("/add-movie")
    public String addMovie(@RequestParam String id,
                           @RequestParam String title,
                           @RequestParam String genre,
                           HttpSession session) {
        if (!"admin".equals(session.getAttribute("role"))) return "redirect:/login";
        movieService.addMovie(new Movie(id, title, genre));
        return "redirect:/admin";
    }

    @PostMapping("/add-show")
    public String addShow(@RequestParam String id,
                          @RequestParam String showTime,
                          @RequestParam String movieId,
                          @RequestParam int screenNumber,
                          HttpSession session,
                          Model model) {
        if (!"admin".equals(session.getAttribute("role"))) return "redirect:/login";
        String error = showService.addShow(new Show(id, showTime, movieId, screenNumber));
        if (error != null) {
            model.addAttribute("showError", error);
            model.addAttribute("movies", movieService.getAllMovies());
            model.addAttribute("shows", showService.getAllShows());
            return "admin";
        }
        return "redirect:/admin";
    }

    @GetMapping("/bookings")
    public String viewBookings(HttpSession session, Model model) {
        if (!"admin".equals(session.getAttribute("role"))) return "redirect:/login";
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "history";
    }

    @PostMapping("/reset")
    public String resetAll(HttpSession session) {
        if (!"admin".equals(session.getAttribute("role"))) return "redirect:/login";
        bookingService.deleteAll();
        showService.deleteAll();
        movieService.deleteAll();
        return "redirect:/admin";
    }
}
