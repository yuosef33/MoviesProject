package com.project.revision.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.revision.model.Movie;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreDto {

    private Long id;
    private String name;
    private List<Movie> movies ;

    public GenreDto() {
    }

    public GenreDto(Long id, String name, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
