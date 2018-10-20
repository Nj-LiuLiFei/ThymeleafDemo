package com.example.demo.service.impl;

import com.example.demo.mapper.FilmMapper;
import com.example.demo.entity.FilmEntity;
import com.example.demo.pagehelper.PageHelper;
import com.example.demo.pagehelper.PageInfo;
import com.example.demo.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("filmService")
public class FilmServiceImpl implements FilmService {

    @Autowired
    @Qualifier("filmMapper")
    private FilmMapper filmMapper;

    @Override
    public PageInfo pageListByQuery(FilmEntity film, int pageOffset, int pageSize) {
        PageHelper.startPage(pageOffset,pageSize);
        return  filmMapper.pageListByQuery(film);
    }
}
