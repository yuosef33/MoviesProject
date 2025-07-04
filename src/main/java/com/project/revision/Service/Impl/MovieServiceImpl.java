package com.project.revision.Service.Impl;

import com.project.revision.Dao.MovieDao;
import com.project.revision.Dto.GenreDto;
import com.project.revision.Dto.MovieDto;
import com.project.revision.Mapper.GenreMapper;
import com.project.revision.Mapper.MovieMapper;
import com.project.revision.Service.MovieService;
import com.project.revision.model.Movie;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieDao movieDao;


    @Override
    public List<MovieDto> getAllmovies(int pageNumber,int pageSize) {
        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Movie> pages = movieDao.findAll(pageable);
        List<Movie> movies=pages.stream().toList();
        return MovieMapper.toDtoList(movies);
    }

    @Override
    public MovieDto getmovie(Long id) {
    Movie movie= movieDao.findById(id).get();
    return MovieMapper.toDTO(movie);
    }

    @Override
    public List<MovieDto> searchMovie(String search,int pageNumber,int pageSize) {
        org.springframework.data.domain.Pageable pageable=PageRequest.of(pageNumber,pageSize);
        Page<Movie>movies=movieDao.findAllByTitleContainingIgnoreCase(search, pageable);
        return MovieMapper.toDtoList(movies.stream().toList());
    }


    @Override
    public List<MovieDto> addMovies(List<MovieDto> movieDtos) {
        List<Movie> saved,movies=new ArrayList<>();
        movies=MovieMapper.toEntityList(movieDtos);
        saved = movieDao.saveAll(movies);
        return MovieMapper.toDtoList(saved);
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto) throws SystemException {
        Movie saved,movie=new Movie();
        movie=MovieMapper.toEntity(movieDto);
       saved = movieDao.save(movie);

       return MovieMapper.toDTO(saved);

    }

    @Override
    public MovieDto editmovie(Long id,MovieDto movieDto) {
        Movie movie=new Movie();
        movie=movieDao.findById(id).get();
        movie.setAllaowedAge(movieDto.getAllaowedAge());
        movie.setTitle(movieDto.getTitle());
        movie.setDirector(movieDto.getDirector());
        movie.setYear(movieDto.getYear());
        movie.setRating(movieDto.getRating());
        movieDao.save(movie);
        return MovieMapper.toDTO(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieDao.deleteById(id);
    }

    @Override
    public List<GenreDto> getallMovieGenres(Long id) {
        Movie movie= movieDao.findById(id).get();
        List<GenreDto> genres= GenreMapper.toDTOList(movie.getGenres());
        return genres;
    }

    @Override
    public int getMoviesNumber() {
        return (int) movieDao.count();
    }

    @Override
    public long getMoviesNumberInSearch(String search) {
        return  movieDao.countByTitleContainingIgnoreCase(search);
    }
}
