package com.project.revision.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.util.List;


@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int allaowedAge;
    private String director;
    private int year;
    private double rating;
    @ManyToMany
    @JoinTable(
            name ="movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    public Movie() {
    }

    public Movie(Long id, String title, int allaowedAge, String director, int year, double rating, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.allaowedAge = allaowedAge;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.genres = genres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getAllaowedAge() {
        return allaowedAge;
    }

    public void setAllaowedAge(int allaowedAge) {
        this.allaowedAge = allaowedAge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
