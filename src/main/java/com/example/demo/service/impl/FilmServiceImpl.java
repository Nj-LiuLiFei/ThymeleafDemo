package com.example.demo.service.impl;

import com.example.demo.mapper.FilmMapper;
import com.example.demo.model.Film;
import com.example.demo.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("filmService")
public class FilmServiceImpl implements FilmService {

    @Autowired
    @Qualifier("filmMapper")
    private FilmMapper filmMapper;

    @Override
    public List<Film> pageListByQuery(Film film) {
        return filmMapper.pageListByQuery(film);
    }
}
