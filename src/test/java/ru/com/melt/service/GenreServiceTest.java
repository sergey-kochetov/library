package ru.com.melt.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.com.melt.model.Comment;
import ru.com.melt.model.Genre;
import ru.com.melt.repository.GenreRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreServiceTest {

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private GenreService genreService;

    @Before
    public void init() {
        reset(genreRepository);
    }

    @Test
    public void whenGetAllComments_thenReturnList() {
        // given
        List<Genre> list = Collections.emptyList();
        // when
        when(genreRepository.findAll()).thenReturn(list);
        List<String> allBooks = genreService.getAllGenres();

        // then
        verify(genreRepository).findAll();
    }

    @Test
    public void whenAddNewGenre_thenReturnTrue() {
        // given
        Genre genre = new Genre("genre");

        // when
        when(genreRepository.findGenreByGenreName(anyString())).thenReturn(Optional.of(genre));
        boolean actual = genreService.addNewGenre(genre);

        // then
        verify(genreRepository).findGenreByGenreName(anyString());
        assertTrue(actual);
    }

    @Test
    public void whenAddNewGenre_thenReturnFalse() {
        // given
        Genre genre = new Genre("genre");

        // when
        when(genreRepository.findGenreByGenreName(anyString())).thenReturn(Optional.ofNullable(null));
        boolean actual = genreService.addNewGenre(genre);

        // then
        verify(genreRepository).findGenreByGenreName(anyString());
        assertFalse(actual);
    }

    @Test
    public void whenDeleteGenre_thenReturnTrue() {
        // given
        String genre = "genre";

        // when
        when(genreRepository.deleteGenreByGenreName(anyString())).thenReturn(1);
        boolean actual = genreService.deleteGenre(genre);

        // then
        verify(genreRepository).deleteGenreByGenreName(anyString());
        assertTrue(actual);
    }

    @Test
    public void whenDeleteGenre_thenReturnFalse() {
        // given
        String genre = "genre";

        // when
        when(genreRepository.deleteGenreByGenreName(anyString())).thenReturn(0);
        boolean actual = genreService.deleteGenre(genre);

        // then
        verify(genreRepository).deleteGenreByGenreName(anyString());
        assertFalse(actual);
    }

}