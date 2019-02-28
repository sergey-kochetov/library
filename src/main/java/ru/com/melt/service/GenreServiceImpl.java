package ru.com.melt.service;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.com.melt.model.Genre;
import ru.com.melt.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(Genre::getGenreName)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf));
    }

    @Override
    public boolean addNewGenre(Genre genre) {
        Optional<Genre> genre1 = genreRepository.findGenreByGenreName(genre.getGenreName());
        if (!genre1.isPresent()) {
            return false;
        }
        genreRepository.save(genre);
        return true;
    }

    @Override
    public boolean deleteGenre(String genreName) {
        int result = genreRepository.deleteGenreByGenreName(genreName);
        return result > 0;
    }
}
