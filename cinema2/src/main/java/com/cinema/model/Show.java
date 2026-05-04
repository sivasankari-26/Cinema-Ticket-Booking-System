package com.cinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "show_time")
    private String showTime;

    @Column(name = "movie_id")
    private String movieId;

    @Column(name = "screen_number")
    private int screenNumber;

    public Show() {}

    public Show(String id, String showTime, String movieId, int screenNumber) {
        this.id = id;
        this.showTime = showTime;
        this.movieId = movieId;
        this.screenNumber = screenNumber;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getShowTime() { return showTime; }
    public void setShowTime(String showTime) { this.showTime = showTime; }

    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }

    public int getScreenNumber() { return screenNumber; }
    public void setScreenNumber(int screenNumber) { this.screenNumber = screenNumber; }
}
