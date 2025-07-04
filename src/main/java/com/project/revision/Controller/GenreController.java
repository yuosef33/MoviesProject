package com.project.revision.Controller;

import com.project.revision.Dto.GenreDto;
import com.project.revision.Dto.MovieDto;
import com.project.revision.Service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }


    @PostMapping("/CreateGenre")
    public ResponseEntity<GenreDto> createGenre(@RequestBody GenreDto genreDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.addGenre(genreDto));
    }

    @PostMapping("/CreateGenres")
    public ResponseEntity<List<GenreDto>> createGenres(@RequestBody List<GenreDto> genreDtos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.addgenres(genreDtos));
    }

    @GetMapping("/GetallGenres")
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        return ResponseEntity.ok(genreService.getallGenres());
    }

    @GetMapping("/getGenre/{id}")
    public ResponseEntity<GenreDto> getGenre(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.getGenre(id));
    }

    @DeleteMapping("/deltegenre/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getGenreMovies/{id}")
    public ResponseEntity<List<MovieDto>> getGenreMoviees(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.getallGenreMovies(id));
    }
}
