package com.example.demo.controller;

import com.example.demo.entity.FilmEntity;
import com.example.demo.model.FilmModel;
import com.example.demo.service.FilmService;
import com.example.demo.util.RegexUtils;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Autowired
    @Qualifier("filmService")
    private FilmService filmService;

    @GetMapping("/pageListByQuery")
    public ResponseEntity pageListByQuery(FilmModel filmModel){

        FilmEntity filmEntity = new FilmEntity();
        filmEntity.setFilm_id(filmModel.getFilm_id());
        filmEntity.setReplacement_cost(filmModel.getReplacement_cost());
        filmEntity.setRental_rate(filmModel.getRental_rate());
        if(RegexUtils.positiveInteger(filmModel.getRental_duration())){
            filmEntity.setRental_duration(NumberUtils.parseNumber(filmModel.getRental_duration(),Integer.class));
        }
        if(RegexUtils.positiveInteger(filmModel.getLength())){
            filmEntity.setLength(NumberUtils.parseNumber(filmModel.getLength(),Integer.class));
        }
        filmEntity.setRating(filmModel.getRating());
        return new ResponseEntity(filmService.pageListByQuery(filmEntity),HttpStatus.OK);
    }
}
