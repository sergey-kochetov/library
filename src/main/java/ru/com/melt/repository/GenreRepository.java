package ru.com.melt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.com.melt.model.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {

    List<Genre> findAll();

    Optional<Genre> findGenreByGenreName(String genreName);

    int deleteGenreByGenreName(String genreName);
}
