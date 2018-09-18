package com.example.demo.mapper;

import com.example.demo.model.Film;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

@Repository("filmMapper")
public interface FilmMapper {
    List<Film> pageListByQuery(Film film);
}
