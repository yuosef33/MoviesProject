package com.project.revision.Service.Impl;

import com.project.revision.Dao.GenreDao;
import com.project.revision.Dto.GenreDto;
import com.project.revision.Dto.MovieDto;
import com.project.revision.Mapper.GenreMapper;
import com.project.revision.Mapper.MovieMapper;
import com.project.revision.Service.GenreService;
import com.project.revision.model.Genre;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<GenreDto> addgenres(List<GenreDto> genreDtos) {
        genreDao.saveAll(GenreMapper.toEntityList(genreDtos));
        return genreDtos;
    }

    @Override
    public List<GenreDto> getallGenres() {
        List<Genre> genres=genreDao.findAll();
        return GenreMapper.toDTOList(genres);
    }

    @Override
    public GenreDto getGenre(Long id) {
        Genre genre=genreDao.findById(id).get();
        return GenreMapper.toDTO(genre);
    }

    @Override
    public GenreDto addGenre(GenreDto genreDto) {
        Genre genre=GenreMapper.toEntity(genreDto);
        genreDao.save(genre);
        return GenreMapper.toDTO(genre);
    }

    @Override
    public void deleteGenre(Long id) {
       Genre genre= genreDao.findById(id).get();
        genreDao.delete(genre);
    }

    @Override
    public List<MovieDto> getallGenreMovies(Long id) {
        Genre genre = genreDao.findById(id).get();
        List<MovieDto> movies= MovieMapper.toDtoList(genre.getMovies());
        return movies ;
    }


}
