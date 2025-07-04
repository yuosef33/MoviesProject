package com.project.revision.Service;

import com.project.revision.Dto.GenreDto;
import com.project.revision.Dto.MovieDto;

import java.util.List;

public interface GenreService {
     List<GenreDto> addgenres(List<GenreDto> genreDtos);
     List<GenreDto> getallGenres();
     GenreDto getGenre(Long id);
     GenreDto addGenre(GenreDto genreDto);
     void deleteGenre(Long id);
     List<MovieDto> getallGenreMovies(Long id);

}
