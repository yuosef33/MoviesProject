package com.project.revision.Service;

import com.project.revision.Dto.GenreDto;
import com.project.revision.Dto.MovieDto;
import com.project.revision.model.Movie;
import jakarta.transaction.SystemException;

import java.util.List;

public interface MovieService {
    List<MovieDto> getAllmovies(int pageNumber,int pageSize);
    MovieDto getmovie(Long id);
    List<MovieDto> searchMovie(String search,int pageNumber,int pageSize);
    List<MovieDto> addMovies(List<MovieDto> movieDtos);
    MovieDto addMovie(MovieDto movieDto) throws SystemException;
    MovieDto editmovie(Long id,MovieDto movieDto);
    void deleteMovie(Long id);
    List<GenreDto> getallMovieGenres(Long id);
    int getMoviesNumber();
    long getMoviesNumberInSearch(String search);
}
