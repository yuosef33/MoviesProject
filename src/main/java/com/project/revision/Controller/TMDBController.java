package com.project.revision.Controller;

import com.project.revision.Dto.GenreDto;
import com.project.revision.Dto.GetOneMovie;
import com.project.revision.Dto.MovieDto;
import com.project.revision.Dto.TMDBMovie;
import com.project.revision.Service.Impl.TMDBMoviesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TMDBAPI")
public class TMDBController {
    private final TMDBMoviesService tmdbMoviesService;

    public TMDBController(TMDBMoviesService tmdbMoviesService) {
        this.tmdbMoviesService = tmdbMoviesService;
    }

    @GetMapping("/getAllMovies")
    public ResponseEntity<List<TMDBMovie>> getAllMovies(@RequestParam int page){
        return ResponseEntity.ok(tmdbMoviesService.getAllPoupler(page));
    }
    @GetMapping("/searchMovies")
    public ResponseEntity<List<TMDBMovie>> searchAllMovies(@RequestParam String key,@RequestParam int page){
        return ResponseEntity.ok(tmdbMoviesService.searchMovie(key,page));
    }
    @GetMapping("/getByGenreId")
    public ResponseEntity<List<TMDBMovie>> getByGenreId(@RequestParam Long id,@RequestParam int page){
        return ResponseEntity.ok(tmdbMoviesService.getAllByGenre(id,page));
    }
    @GetMapping("/getallGenres")
    public ResponseEntity<List<GenreDto>> getAllGenres(){
        return ResponseEntity.ok(tmdbMoviesService.getAllGenre());
    }
    @GetMapping("/get/{movieid}/movie")
    public ResponseEntity<GetOneMovie> getMovie(@PathVariable("movieid") Long id){
        return ResponseEntity.ok(tmdbMoviesService.getMovieById(id));
    }




}
