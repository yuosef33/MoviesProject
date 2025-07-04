package com.project.revision.Dao;

import com.project.revision.model.Genre;
import com.project.revision.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreDao extends JpaRepository<Genre,Long> {

}
