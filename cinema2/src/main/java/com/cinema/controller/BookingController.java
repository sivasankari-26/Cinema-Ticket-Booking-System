package com.cinema.controller;

import com.cinema.model.Show;
import com.cinema.service.BookingService;
import com.cinema.service.ShowService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ShowService showService;

    // Show booking form - receives multiple seat numbers
    @GetMapping("/booking")
    public String bookingForm(@RequestParam String showId,
                              @RequestParam List<String> seatNumbers,
                              HttpSession session,
                              Model model) {
        if (session.getAttribute("role") == null) return "redirect:/login";

        Optional<Show> show = showService.getShowById(showId);
        if (show.isEmpty()) return "redirect:/movies";

        model.addAttribute("show", show.get());
        model.addAttribute("seatNumbers", seatNumbers);
        return "booking";
    }

    // Process booking for multiple seats
    @PostMapping("/booking/confirm")
    public String confirmBooking(@RequestParam String showId,
                                 @RequestParam List<String> seatNumbers,
                                 @RequestParam String customerName,
                                 @RequestParam String phone,
                                 @RequestParam String paymentType,
                                 HttpSession session,
                                 Model model) {
        if (session.getAttribute("role") == null) return "redirect:/login";

        boolean isLucky = bookingService.bookSeats(showId, seatNumbers,
                                                   customerName, phone, paymentType);

        model.addAttribute("customerName", customerName);
        model.addAttribute("seatNumbers", seatNumbers);
        model.addAttribute("showId", showId);
        model.addAttribute("paymentType", paymentType);
        model.addAttribute("lucky", isLucky);
        return "confirmation";
    }
}
