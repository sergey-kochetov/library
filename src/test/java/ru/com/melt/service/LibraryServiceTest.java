package ru.com.melt.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.com.melt.repository.AuthorRepository;
import ru.com.melt.repository.BookRepository;
import ru.com.melt.repository.CustomerRepository;

import static org.mockito.Mockito.reset;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryServiceTest {

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private LibraryService libraryService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Before
    public void init() {
        reset(authorRepository);
        reset(bookRepository);
        reset(customerRepository);
    }
    @Test
    public void test() {

    }



}