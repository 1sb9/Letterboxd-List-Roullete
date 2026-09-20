package org.example.Service;

import org.example.Model.Film;

import java.util.List;

public interface FilmRoullete {

    Film getRandomFilm(List<Film> films);
}
