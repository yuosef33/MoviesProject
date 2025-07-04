package com.project.revision.Mapper;

import com.project.revision.Dto.GenreDto;
import com.project.revision.Dto.MovieDto;
import com.project.revision.model.Genre;
import com.project.revision.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieMapper {
    public static Movie toEntity(MovieDto movieDto){
        Movie movie=new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setAllaowedAge(movieDto.getAllaowedAge());
        movie.setDirector(movieDto.getDirector());
        movie.setYear(movieDto.getYear());
        movie.setRating(movieDto.getRating());
        for(Genre genre:movieDto.getGenres()){
            genre.setMovies(null);
        }
        movie.setGenres(movieDto.getGenres());
        return movie;
    }
    public static MovieDto toDTO(Movie movie) {
        MovieDto dto = new MovieDto();
        dto.setTitle(movie.getTitle());
        dto.setDirector(movie.getDirector());
        dto.setAllaowedAge(movie.getAllaowedAge());
        dto.setYear(movie.getYear());
        dto.setRating(movie.getRating());
        for(Genre genre:movie.getGenres()){
            genre.setMovies(null);
        }
        dto.setGenres(movie.getGenres());
        return dto;
    }

    public static List<Movie> toEntityList(List<MovieDto> movieDtos){
        List<Movie> movies= new ArrayList<>();
        for(MovieDto movie: movieDtos){
            movies.add(toEntity(movie));
        }
        return movies;

    }
    public static List<MovieDto> toDtoList(List<Movie> movies){
        List<MovieDto> moviesDto= new ArrayList<>();
        for(Movie movie: movies){
            moviesDto.add(toDTO(movie));
        }
        return moviesDto;

    }

}
