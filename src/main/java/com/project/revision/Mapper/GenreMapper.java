package com.project.revision.Mapper;

import com.project.revision.Dto.GenreDto;
import com.project.revision.model.Genre;
import java.util.ArrayList;
import java.util.List;

public class GenreMapper {

    public static GenreDto toDTO(Genre genre) {
        if (genre == null) return null;

        GenreDto dto = new GenreDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setMovies(genre.getMovies());

        return dto;
    }

    public static Genre toEntity(GenreDto dto) {
        if (dto == null) return null;

        Genre genre = new Genre();
        genre.setId(dto.getId());
        genre.setName(dto.getName());
        genre.setMovies(dto.getMovies());

        return genre;
    }

    public static List<GenreDto> toDTOList(List<Genre> genres) {
        List<GenreDto> dtoList = new ArrayList<>();
        if (genres == null) return dtoList;

        for (Genre genre : genres) {
            dtoList.add(toDTO(genre));
        }
        return dtoList;
    }

    public static List<Genre> toEntityList(List<GenreDto> dtos) {
        List<Genre> entityList = new ArrayList<>();
        if (dtos == null) return entityList;

        for (GenreDto dto : dtos) {
            entityList.add(toEntity(dto));
        }
        return entityList;
    }
}
