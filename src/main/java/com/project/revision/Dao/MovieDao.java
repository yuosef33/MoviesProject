package com.project.revision.Dao;

import com.project.revision.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieDao extends JpaRepository<Movie,Long> {
Page<Movie> findAllByTitleContainingIgnoreCase(String search,Pageable pageable);
long count();
    long countByTitleContainingIgnoreCase(String search);
}
