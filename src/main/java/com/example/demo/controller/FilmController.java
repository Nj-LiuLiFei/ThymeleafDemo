package com.example.demo.controller;

import com.example.demo.entity.FilmEntity;
import com.example.demo.model.FilmModel;
import com.example.demo.pagehelper.PageInfo;
import com.example.demo.service.FilmService;
import com.example.demo.util.RegexUtils;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Autowired
    @Qualifier("filmService")
    private FilmService filmService;

    @PostMapping("/pageListByQuery")
    public ResponseEntity pageListByQuery(@RequestParam(value = "film_id",required = false) Integer film_id,
                                          @RequestParam(value = "rental_duration",required = false) Integer rental_duration,
                                          @RequestParam(value = "rental_rate",required = false) String rental_rate,
                                          @RequestParam(value = "length",required = false) Integer length,
                                          @RequestParam(value = "replacement_cost",required = false) String replacement_cost,
                                          @RequestParam(value = "rating",required = false) String rating,
                                          @RequestParam(value = "pageOffset") int pageOffset,
                                          @RequestParam(value = "pageSize")int pageSize){
        FilmEntity filmEntity = new FilmEntity();
        filmEntity.setFilm_id(film_id);
        filmEntity.setRental_duration(rental_duration);
        filmEntity.setRental_rate(rental_rate);
        filmEntity.setLength(length);
        filmEntity.setReplacement_cost(replacement_cost);
        filmEntity.setRating(rating);
        Map<String,Object> response = new HashMap<String, Object>();
        PageInfo pageInfo = filmService.pageListByQuery(filmEntity,pageOffset,pageSize);
        response.put("pageTotal",pageInfo.getTotal());
        response.put("list",pageInfo.getList());
        return new ResponseEntity(response,HttpStatus.OK);
    }
}
