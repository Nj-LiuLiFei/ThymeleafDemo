package com.example.demo.mapper;

import com.example.demo.entity.FilmEntity;
import com.example.demo.pagehelper.PageInfo;
import org.springframework.stereotype.Repository;

@Repository("filmMapper")
public interface FilmMapper {
    PageInfo pageListByQuery(FilmEntity film);
}
