package com.example.demo.mapper;

import com.example.demo.entity.FilmEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("filmMapper")
public interface FilmMapper {
    List<FilmEntity> pageListByQuery(FilmEntity film);
}
