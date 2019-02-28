package ru.com.melt.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.com.melt.model.Author;
import ru.com.melt.repository.AuthorRepository;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceTest {

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Before
    public void init() {
        reset(authorRepository);
    }

    @Test
    public void getAllAuthorsNames() {
        // given

        // when
        List<String> allAuthorsNames = authorService.getAllAuthorsNames();

        // then
        verify(authorRepository).findAllAuthorsNames();
    }

    @Test
    public void addNewAuthor_thenReturnTrue() {
        // given
        Author author = new Author("name1");
        author.setId(1L);

        // when
        when(authorRepository.save(any())).thenReturn(author);
        boolean actual = authorService.addNewAuthor(author);

        // then
        assertTrue(actual);
    }

    @Test
    public void addNewAuthor_thenReturnFalse() {
        // given
        Author author = new Author("name1");

        // when
        when(authorRepository.save(any())).thenReturn(author);
        boolean actual = authorService.addNewAuthor(author);

        // then
        assertFalse(actual);
    }

    @Test(expected = NullPointerException.class)
    public void addNewAuthor_thenThrowExc() {
        // given
        Author author = null;

        // when
        when(authorRepository.save(any())).thenThrow(NullPointerException.class);
        boolean actual = authorService.addNewAuthor(author);

        // then
        assertTrue(actual);
    }

    @Test
    public void deleteAuthorById_thenReturnTrue() {
        // given
        Long id = 1L;

        // when
        when(authorRepository.deleteAuthorById(id)).thenReturn(1);
        boolean actual = authorService.deleteAuthorById(id);

        // then
        verify(authorRepository).deleteAuthorById(id);
        assertTrue(actual);
    }

    @Test
    public void deleteAuthorById_thenReturnFalse() {
        // given
        Long id = 0L;

        // when
        when(authorRepository.deleteAuthorById(id)).thenReturn(0);
        boolean actual = authorService.deleteAuthorById(id);

        // then
        verify(authorRepository).deleteAuthorById(id);
        assertFalse(actual);
    }

    @Test(expected = NullPointerException.class)
    public void deleteAuthorById_thenThrowException() {
        // given
        Long id = null;

        // when
        when(authorRepository.deleteAuthorById(id)).thenThrow(NullPointerException.class);
        boolean actual = authorService.deleteAuthorById(id);

        // then
        verify(authorRepository).deleteAuthorById(id);
    }

}