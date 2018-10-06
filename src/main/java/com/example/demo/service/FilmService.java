package com.example.demo.service;

import com.example.demo.common.Page;
import com.example.demo.entity.FilmEntity;

import java.util.List;

public interface FilmService {
    Page<FilmEntity> pageListByQuery(FilmEntity film);
}
