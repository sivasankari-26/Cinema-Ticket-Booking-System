package com.cinema.controller;

import com.cinema.model.Movie;
import com.cinema.model.Show;
import com.cinema.model.User;
import com.cinema.repository.UserRepository;
import com.cinema.service.MovieService;
import com.cinema.service.ShowService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowService showService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        if (email.equals("admin@gmail.com") && password.equals("Siva$123")) {
            session.setAttribute("role", "admin");
            session.setAttribute("email", email);
            return "redirect:/admin";
        }

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            session.setAttribute("role", "user");
            session.setAttribute("email", email);
            session.setAttribute("userName", userOpt.get().getName());
            return "redirect:/movies";
        }

        model.addAttribute("error", "Invalid email or password. Please register first.");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String phone,
                             Model model) {
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Email already registered! Please login.");
            return "register";
        }
        userRepository.save(new User(name, email, password, phone));
        model.addAttribute("success", "Registration successful! Please login.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/movies")
    public String moviesPage(HttpSession session, Model model) {
        if (session.getAttribute("role") == null) return "redirect:/login";
        model.addAttribute("movies", movieService.getAllMovies());
        return "movies";
    }

    @GetMapping("/shows")
    public String showsPage(@RequestParam String movieId, HttpSession session, Model model) {
        if (session.getAttribute("role") == null) return "redirect:/login";
        Optional<Movie> movie = movieService.getMovieById(movieId);
        if (movie.isEmpty()) return "redirect:/movies";
        model.addAttribute("movie", movie.get());
        model.addAttribute("shows", showService.getShowsByMovieId(movieId));
        return "shows";
    }

    @GetMapping("/seats")
    public String seatsPage(@RequestParam String showId, HttpSession session, Model model) {
        if (session.getAttribute("role") == null) return "redirect:/login";
        Optional<Show> show = showService.getShowById(showId);
        if (show.isEmpty()) return "redirect:/movies";
        model.addAttribute("show", show.get());
        model.addAttribute("seats", showService.getSeatsForShow(showId));
        return "seats";
    }
}
