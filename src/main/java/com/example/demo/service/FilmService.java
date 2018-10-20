package com.example.demo.service;

import com.example.demo.entity.FilmEntity;
import com.example.demo.pagehelper.PageInfo;

public interface FilmService {
    PageInfo pageListByQuery(FilmEntity film, int pageOffset, int pageSize);
}
