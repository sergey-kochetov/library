package ru.com.melt.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.com.melt.model.Book;
import ru.com.melt.repository.BookRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Before
    public void init() {
        reset(bookRepository);
    }

    @Test
    public void getAllBooks() {
        // given
        List<Book> list = Collections.emptyList();
        // when
        when(bookRepository.findAll()).thenReturn(list);
        List<Book> allBooks = bookService.getAllBooks();

        // then
        verify(bookRepository).findAll();
    }

    @Test
    public void getBookById_thenSuccess() {
        // given
        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        book.setTitle("title1");

        // when
        when(bookRepository.findBookById(id)).thenReturn(Optional.of(book));
        Book actual = bookService.getBookById(id);

        // then
        verify(bookRepository).findBookById(id);
    }

    @Test(expected = RuntimeException.class)
    public void getBookById_thenThrowExc() {
        // given
        Long id = 0L;

        // when
        when(bookRepository.findBookById(id)).thenThrow(RuntimeException.class);
        Book actual = bookService.getBookById(id);

        // then
        verify(bookRepository).findBookById(id);
    }

    @Test
    public void deleteBookById_thenReturnTrue() {
        // given
        Long id = 1L;

        // when
        when(bookRepository.deleteBookById(id)).thenReturn(1);
        boolean actual = bookService.deleteBookById(id);

        // then
        verify(bookRepository).deleteBookById(id);
        assertTrue(actual);
    }

    @Test
    public void deleteBookById_thenReturnFalse() {
        // given
        Long id = 0L;

        // when
        when(bookRepository.deleteBookById(id)).thenReturn(0);
        boolean actual = bookService.deleteBookById(id);

        // then
        verify(bookRepository).deleteBookById(id);
        assertFalse(actual);
    }


}