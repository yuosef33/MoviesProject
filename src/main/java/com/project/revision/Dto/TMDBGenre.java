package com.project.revision.Dto;

import java.util.List;

public class TMDBGenre {
    private List<GenreDto> genres;

    public TMDBGenre() {
    }

    public TMDBGenre(List<GenreDto> genres) {
        this.genres = genres;
    }

    public List<GenreDto> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDto> genres) {
        this.genres = genres;
    }
}
