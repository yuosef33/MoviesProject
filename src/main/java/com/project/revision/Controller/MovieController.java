package com.project.revision.Controller;
import com.project.revision.Dto.MovieDto;
import com.project.revision.Service.MovieService;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/getAllMovies")
    public ResponseEntity<List<MovieDto>> getAllMovies(@RequestParam int page,@RequestParam int size){
        return ResponseEntity.ok(movieService.getAllmovies(page,size));
}
    @PostMapping("/addMovie")
    public ResponseEntity<MovieDto> addMovie(@Valid @RequestBody MovieDto movieDto) throws SystemException {
   MovieDto saved= movieService.addMovie(movieDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
}

    @PostMapping("/addMovies")
    public ResponseEntity<List<MovieDto>> addMovies(@Valid @RequestBody List<MovieDto> movieDtos){
        List<MovieDto> saved= movieService.addMovies(movieDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping("/searchMovies/{search}")
    public ResponseEntity<List<MovieDto>> searchmovies(@PathVariable("search") String search,@RequestParam int page,@RequestParam int size){
        return ResponseEntity.ok(movieService.searchMovie(search, page, size));
    }
    @GetMapping("/getMovieByid")
    public ResponseEntity<MovieDto> getmovieById(@RequestParam Long id){
        return ResponseEntity.ok(movieService.getmovie(id));
    }
    @PutMapping("/updateMovie/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id,@RequestBody MovieDto movieDto){
        return ResponseEntity.ok(movieService.editmovie(id,movieDto));
    }
    @DeleteMapping("/deleteMovie/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/countMovies")
    public ResponseEntity<Integer> moviesCounter(){
        return ResponseEntity.ok(movieService.getMoviesNumber());
    }
    @GetMapping("/countMoviesInSearch/{search}")
    public ResponseEntity<Long> moviesCounterinsearch(@PathVariable String search){
        return ResponseEntity.ok(movieService.getMoviesNumberInSearch(search));
    }
}
