package ru.com.melt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.com.melt.model.Genre;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager manager;

    @Test(expected = NullPointerException.class)
    public void whenAddGenreNull_thenThrowExc() {
        // given
        Genre genre1 = addGenre(null);
    }

    @Test
    public void whenAddTwoGenre_thenReturnList() {
        // given
        Genre genre1 = addGenre("genre1");
        Genre genre2 = addGenre("genre2");

        // when

        List<Genre> genres = genreRepository.findAll();

        // then
        assertEquals(2, genres.size());
        assertEquals("comment text1, comment text2", genres.toString());
    }

    @Test
    public void whenFindGenreByName_thenNoOk() {
        // given
        Genre genre1 = addGenre("genre1");
        Genre genre2 = addGenre("genre2");

        // when

        Optional<Genre> optionalGenre = genreRepository.findGenreByGenreName("genre");

        // then
        assertFalse(optionalGenre.isPresent());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenFindGenreNameIsNull_thenThrowExc() {
        // given
        Genre genre1 = addGenre("genre1");
        Genre genre2 = addGenre("genre2");

        // when

        Optional<Genre> optionalGenre = genreRepository.findGenreByGenreName(null);

        // then
        assertEquals(2, optionalGenre.get());
    }

    @Test
    public void whenFindGenreByName_thenOk() {
        // given
        Genre genre1 = addGenre("genre1");
        Genre genre2 = addGenre("genre2");

        // when

        Optional<Genre> optionalGenre = genreRepository.findGenreByGenreName("genre1");

        // then
        assertTrue(optionalGenre.isPresent());
        assertEquals("Genre(genreName=genre1)", optionalGenre.get().toString());
    }

    private Genre addGenre(String testName) {
        Genre genre = new Genre();
        genre.setGenreName(testName);

        return manager.persist(genre);
    }
}