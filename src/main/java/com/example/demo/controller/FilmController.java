package com.example.demo.controller;

import com.example.demo.model.Film;
import com.example.demo.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Autowired
    @Qualifier("filmService")
    private FilmService filmService;

    @GetMapping("/pageListByQuery")
    public ResponseEntity pageListByQuery(Film film){
        return new ResponseEntity(filmService.pageListByQuery(film),HttpStatus.OK);
    }
}
