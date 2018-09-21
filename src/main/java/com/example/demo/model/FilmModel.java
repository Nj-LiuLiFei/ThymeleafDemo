package com.example.demo.model;

public class FilmModel extends  BasePageModel {
    String film_id;
    String rental_duration;
    String rental_rate;
    String length;
    String replacement_cost;
    String rating;

    public String getFilm_id() {
        return film_id;
    }

    public void setFilm_id(String film_id) {
        this.film_id = film_id;
    }

    public String getRental_duration() {
        return rental_duration;
    }

    public void setRental_duration(String rental_duration) {
        this.rental_duration = rental_duration;
    }

    public String getRental_rate() {
        return rental_rate;
    }

    public void setRental_rate(String rental_rate) {
        this.rental_rate = rental_rate;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getReplacement_cost() {
        return replacement_cost;
    }

    public void setReplacement_cost(String replacement_cost) {
        this.replacement_cost = replacement_cost;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
