package com.project.revision.Service.Impl;

import com.project.revision.Dto.*;
import com.project.revision.model.Genre;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class TMDBMoviesService {

    private final RestTemplate restTemplate;
    private final String API_KEY="f853563da39f2ff4e59b884d4d966918";
    public TMDBMoviesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<TMDBMovie> getAllPoupler(int pageNumber){
        String url="https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&page="+pageNumber;
      TMDBMovieWrapper Movies=restTemplate.getForObject(url, TMDBMovieWrapper.class);
      return Movies.getResults();
    }

    public GetOneMovie getMovieById(Long id){
        String url="https://api.themoviedb.org/3/movie/"+id+"?api_key="+API_KEY;
        GetOneMovie Movie=restTemplate.getForObject(url, GetOneMovie.class);
        return Movie;
    }


    public List<TMDBMovie> searchMovie(String key ,int pageNumber){
        String url="https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&query="+key+"&page="+pageNumber;
        TMDBMovieWrapper Movies=restTemplate.getForObject(url, TMDBMovieWrapper.class);
        return Movies.getResults();
    }

    public List<TMDBMovie> getAllByGenre(Long id,int pageNumber){
        String url="https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&with_genres="+id+"&page="+pageNumber;
        TMDBMovieWrapper Movies=restTemplate.getForObject(url, TMDBMovieWrapper.class);
        return Movies.getResults();
    }

    public List<GenreDto> getAllGenre() {
        String url="https://api.themoviedb.org/3/genre/movie/list?api_key="+API_KEY;
       TMDBGenre genres=restTemplate.getForObject(url, TMDBGenre.class);
        return genres.getGenres();
    }

}
