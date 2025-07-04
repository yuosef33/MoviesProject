package com.project.revision.Dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.revision.model.Genre;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDto {

    private Long id;

        @NotBlank(message = "Title is required")
        private String title;

        private int allaowedAge;
        @NotBlank(message = "Director is required")
        private String director;

        private int year;
        @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be >= 0.0")
        @DecimalMax(value = "10.0", inclusive = true, message = "Rating must be <= 10.0")
        private double rating;
        private List<Genre> genres;

    public MovieDto() {
    }

    public MovieDto(Long id, String title, int allaowedAge, String director, int year, double rating, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.allaowedAge = allaowedAge;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Title is required") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title is required") String title) {
        this.title = title;
    }

    public int getAllaowedAge() {
        return allaowedAge;
    }

    public void setAllaowedAge(int allaowedAge) {
        this.allaowedAge = allaowedAge;
    }

    public @NotBlank(message = "Director is required") String getDirector() {
        return director;
    }

    public void setDirector(@NotBlank(message = "Director is required") String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be >= 0.0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Rating must be <= 10.0")
    public double getRating() {
        return rating;
    }

    public void setRating(@DecimalMin(value = "0.0", inclusive = true, message = "Rating must be >= 0.0") @DecimalMax(value = "10.0", inclusive = true, message = "Rating must be <= 10.0") double rating) {
        this.rating = rating;
    }
}
