package com.example.demo.service;

import com.example.demo.model.Film;

import java.util.List;

public interface FilmService {
    List<Film> pageListByQuery(Film film);
}
