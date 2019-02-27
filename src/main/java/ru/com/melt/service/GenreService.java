package ru.com.melt.service;

import ru.com.melt.model.Genre;

import java.util.List;

public interface GenreService {

    List<String> getAllGenres();

    boolean addNewGenre(Genre genre);
}
