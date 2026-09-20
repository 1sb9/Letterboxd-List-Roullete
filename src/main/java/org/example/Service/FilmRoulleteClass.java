package org.example.Service;

import org.example.Model.Film;

import java.util.List;
import java.util.Random;


public class FilmRoulleteClass implements FilmRoullete {

    @Override
    public Film getRandomFilm(List<Film> films) {
        if  (films.isEmpty()) return null;
        Random rand = new Random();
        return films.get(rand.nextInt(films.size()));

    }

}
