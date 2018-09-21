package com.example.demo.service;

import com.example.demo.entity.FilmEntity;

import java.util.List;

public interface FilmService {
    List<FilmEntity> pageListByQuery(FilmEntity film);
}
