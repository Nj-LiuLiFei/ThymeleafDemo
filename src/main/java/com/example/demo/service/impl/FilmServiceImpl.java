package com.example.demo.service.impl;

import com.example.demo.common.Page;
import com.example.demo.mapper.FilmMapper;
import com.example.demo.entity.FilmEntity;
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
    public Page<FilmEntity> pageListByQuery(FilmEntity film) {
        return filmMapper.pageListByQuery(film);
    }
}
