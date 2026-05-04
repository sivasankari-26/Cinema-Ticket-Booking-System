package com.cinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "show_id")
    private String showId;

    @Column(name = "seat_number")
    private String seatNumber;

    // true = AVAILABLE, false = BOOKED
    @Column(name = "status")
    private boolean status = true;

    public Seat() {}

    public Seat(String showId, String seatNumber) {
        this.showId = showId;
        this.seatNumber = seatNumber;
        this.status = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getShowId() { return showId; }
    public void setShowId(String showId) { this.showId = showId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}
